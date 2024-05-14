package com.tinyhappytrip.story.service;

import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.story.domain.Story;
import com.tinyhappytrip.story.domain.StoryComment;
import com.tinyhappytrip.story.domain.StoryImage;
import com.tinyhappytrip.story.domain.StoryReply;
import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;
import com.tinyhappytrip.story.mapper.*;
import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {
    private final StoryMapper storyMapper;
    private final StoryHashTagMapper storyHashTagMapper;
    private final StoryTagMapper storyTagMapper;
    private final StoryImageMapper storyImageMapper;
    private final StoryLikeMapper storyLikeMapper;
    private final StoryCommentMapper storyCommentMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public int createStory(String basePath, StoryRequestDto.Create create) {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            create.setImagePaths(saveFiles(basePath, create.getImages()));
            storyMapper.insert(userId, create);
            storyHashTagMapper.insert(create);
            storyTagMapper.insert(create);
            storyImageMapper.insert(create);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteStory(Long storyId) {
        List<String> storyImages = storyImageMapper.selectAllByStoryId(storyId);
        storyImages.stream()
                .map(File::new)
                .forEach(File::delete);
        return storyMapper.delete(storyId, SecurityUtil.getCurrentUserId());
    }

    @Override
    public int updateStory(Long storyId, StoryRequestDto.Update story) {
        return storyMapper.update(storyId, SecurityUtil.getCurrentUserId(), story);
    }

    @Override
    public int setStoryLike(Long storyId) {
        Long userId = SecurityUtil.getCurrentUserId();
        if (storyLikeMapper.selectCountByStoryIdAndUserId(storyId, userId) == 1) {
            return storyLikeMapper.delete(storyId, userId);
        }
        return storyLikeMapper.insert(storyId, userId);
    }

    @Override
    public List<StoryResponseDto.StoryInfo> getAllUserStory(Long userId) {
        return storyMapper.selectAllByUserId(userId)
                .stream()
                .map(story -> {
                    StoryResponseDto.StoryInfo storyInfo = StoryResponseDto.StoryInfo.from(story);
                    setStoryInfo(storyInfo);
                    return storyInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<StoryResponseDto.StoryInfo> getAllStory() {
        return storyMapper.selectUserStoriesByUserId(SecurityUtil.getCurrentUserId())
                .stream()
                .map(story -> {
                    StoryResponseDto.StoryInfo storyInfo = StoryResponseDto.StoryInfo.from(story);
                    setStoryInfo(storyInfo);
                    return storyInfo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public StoryResponseDto.StoryInfo getStory(Long storyId) {
        Story story = storyMapper.selectByStoryId(storyId);
        StoryResponseDto.StoryInfo storyInfo = StoryResponseDto.StoryInfo.from(story);
        setStoryInfo(storyInfo);
        return storyInfo;
    }

    @Override
    public int addComment(Long storyId, String type, StoryRequestDto.Comment comment) {
        return storyCommentMapper.insert(storyId, type, SecurityUtil.getCurrentUserId(), comment);
    }

    @Override
    public int deleteComment(Long storyCommentId) {
        Long userId = SecurityUtil.getCurrentUserId();
        return storyCommentMapper.delete(storyCommentId, userId);
    }

    public List<StoryImage> saveFiles(String basePath, MultipartFile[] files) throws IOException {
        String yyyyMm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String uploadPath = basePath + File.separator + yyyyMm;
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        List<StoryImage> imageInfoList = new ArrayList<>();
        for (MultipartFile file : files) {
            System.out.println(file);
            String originalFileName = file.getOriginalFilename();
            String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
            String fullPath = uploadPath + File.separator + storedFileName;
            System.out.println(fullPath);
            File dest = new File(fullPath);
            file.transferTo(dest);
            StoryImage storyImage = new StoryImage();
            storyImage.setStoryImageName(originalFileName);
            storyImage.setStoryImagePath(fullPath);
            imageInfoList.add(storyImage);
        }
        return imageInfoList;
    }

    private void setStoryInfo(StoryResponseDto.StoryInfo storyInfo) {
        storyInfo.setHashtags(storyHashTagMapper.selectHashTagByStoryId(storyInfo.getStoryId()));
        storyInfo.setTags(storyTagMapper.selectUserIdByStoryId(storyInfo.getStoryId()));
        storyInfo.setImages(storyImageMapper.selectAllByStoryId(storyInfo.getStoryId()));
        storyInfo.setLikeCount(storyLikeMapper.selectCountByStoryId(storyInfo.getStoryId()));
        List<StoryComment> storyCommentList = storyCommentMapper.selectCommentByStoryId(storyInfo.getStoryId());
        List<StoryResponseDto.Comment> comments = storyCommentList.stream()
                .map(storyComment -> {
                    User user = userMapper.selectById(storyComment.getUserId()).get();
                    List<StoryReply> storyReplies = storyCommentMapper.selectReplyByStoryCommentId(storyComment.getStoryCommentId());
                    return StoryResponseDto.Comment.from(user, storyComment, StoryResponseDto.Reply.from(user, storyReplies));
                })
                .collect(Collectors.toList());
        storyInfo.setComments(comments);
    }
}

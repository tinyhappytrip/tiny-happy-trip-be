package com.tinyhappytrip.story.service;

import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.story.domain.Story;
import com.tinyhappytrip.story.dto.StoryRequest;
import com.tinyhappytrip.story.dto.StoryResponse;
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
    private final StoryHashtagMapper storyHashtagMapper;
    private final StoryTagMapper storyTagMapper;
    private final StoryImageMapper storyImageMapper;
    private final StoryLikeMapper storyLikeMapper;
    private final StoryCommentMapper storyCommentMapper;
    private final StoryReplyMapper storyReplyMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public int createStory(String basePath, StoryRequest.CreateDto createDto) throws Exception {
        Long userId = SecurityUtil.getCurrentUserId();
        List<String> storyImages = saveFiles(basePath, createDto.getImageFiles());
        Story story = createDto.toEntity();
        storyMapper.insert(userId, story);
        storyHashtagMapper.insert(story.getStoryId(), createDto.getHashtags());
        storyTagMapper.insert(story.getStoryId(), createDto.getTags());
        storyImageMapper.insert(story.getStoryId(), storyImages);
        return 1;
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
    public int updateStory(Long storyId, StoryRequest.UpdateDto story) {
        return storyMapper.update(storyId, SecurityUtil.getCurrentUserId(), story.toEntity());
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
    public List<StoryResponse.StoryDetailDto> getAllUserStory(Long userId) {
        return storyMapper.selectAllByUserId(userId)
                .stream()
                .map(this::getStoryDetail)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoryResponse.StoryDetailDto> getAllStory() {
        return storyMapper.selectUserStoriesByUserId(SecurityUtil.getCurrentUserId())
                .stream()
                .map(this::getStoryDetail)
                .collect(Collectors.toList());
    }

    @Override
    public StoryResponse.StoryDetailDto getStory(Long storyId) {
        return getStoryDetail(storyMapper.selectByStoryId(storyId));
    }

    @Override
    public int addComment(Long storyId, String content) {
        return storyCommentMapper.insert(SecurityUtil.getCurrentUserId(), storyId, content);
    }

    @Override
    public int addReply(Long storyCommentId, String content) {
        return storyReplyMapper.insert(SecurityUtil.getCurrentUserId(), storyCommentId, content);
    }

    @Override
    public int removeComment(Long storyCommentId) {
        return storyCommentMapper.delete(SecurityUtil.getCurrentUserId(), storyCommentId);
    }

    @Override
    public int removeReply(Long storyReplyId) {
        return storyReplyMapper.delete(SecurityUtil.getCurrentUserId(), storyReplyId);
    }

    @Override
    public int editComment(Long storyCommentId, String content) {
        return storyCommentMapper.update(SecurityUtil.getCurrentUserId(), storyCommentId, content);
    }

    @Override
    public int editReply(Long storyReplyId, String content) {
        return storyReplyMapper.update(SecurityUtil.getCurrentUserId(), storyReplyId, content);
    }

    @Override
    public List<StoryResponse.StoryOverviewDto> getAllLikeStory() {
        return storyLikeMapper.selectStoryIdByUserId(SecurityUtil.getCurrentUserId()).stream()
                .map((storyId) -> storyMapper.selectByStoryId(storyId))
                .collect(Collectors.toList()).stream()
                .map(this::getStoryOverview)
                .collect(Collectors.toList());
    }

    public List<String> saveFiles(String basePath, MultipartFile[] imageFiles) throws IOException {
        String yyyyMm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String uploadPath = basePath + File.separator + yyyyMm;
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        List<String> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            String storedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String storyImage = uploadPath + File.separator + storedFileName;
            File dest = new File(storyImage);
            file.transferTo(dest);
            images.add(storyImage);
        }
        return images;
    }

    private StoryResponse.StoryDetailDto getStoryDetail(Story story) {
        List<String> hashtags = storyHashtagMapper.selectHashtagByStoryId(story.getStoryId());
        List<String> tags = storyTagMapper.selectUserIdByStoryId(story.getStoryId());
        List<String> images = storyImageMapper.selectAllByStoryId(story.getStoryId());
        Long likeCount = storyLikeMapper.selectCountByStoryId(story.getStoryId());
        boolean isLike = storyLikeMapper.selectCountByStoryIdAndUserId(story.getStoryId(), SecurityUtil.getCurrentUserId()) == 1 ? true : false;
        User user = userMapper.selectByUserId(story.getUserId()).get();
        List<StoryResponse.StoryCommentDto> storyComments = storyCommentMapper.selectByStoryId(story.getStoryId()).stream()
                .map(storyComment -> {
                    List<StoryResponse.StoryReplyDto> replies = storyReplyMapper.selectByStoryCommentId(storyComment.getStoryCommentId()).stream()
                            .map(storyReply -> StoryResponse.StoryReplyDto.toResponseDto(userMapper.selectByUserId(storyReply.getUserId()).get(), storyReply))
                            .collect(Collectors.toList());
                    return StoryResponse.StoryCommentDto.toResponseDto(userMapper.selectByUserId(storyComment.getUserId()).get(), storyComment, replies);
                })
                .collect(Collectors.toList());
        return StoryResponse.StoryDetailDto.toResponseDto(story, user, hashtags, tags, images, storyComments, likeCount, isLike);
    }

    private StoryResponse.StoryOverviewDto getStoryOverview(Story story) {
        List<String> hashtags = storyHashtagMapper.selectHashtagByStoryId(story.getStoryId());
        List<String> tags = storyTagMapper.selectUserIdByStoryId(story.getStoryId());
        List<String> images = storyImageMapper.selectAllByStoryId(story.getStoryId());
        Long likeCount = storyLikeMapper.selectCountByStoryId(story.getStoryId());
        boolean isLike = storyLikeMapper.selectCountByStoryIdAndUserId(story.getStoryId(), SecurityUtil.getCurrentUserId()) == 1 ? true : false;
        User user = userMapper.selectByUserId(story.getUserId()).get();
        return StoryResponse.StoryOverviewDto.toResponseDto(story, hashtags, tags, images, likeCount, isLike, user);
    }
}

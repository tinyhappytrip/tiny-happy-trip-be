package com.tinyhappytrip.story.service;

import com.tinyhappytrip.story.dto.FileInfoDto;
import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;
import com.tinyhappytrip.story.mapper.StoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryMapper storyMapper;

    @Override
    public int createStory(StoryRequestDto.Create story, int userId) throws IOException {

        // 스토리 생성하기
        int storyId = insertStory(story, userId);

        // 스토리 이미지 넣기
        int imageResult = insertStoryImage(story, storyId);

        // 스토리 해시태그 넣기
        int hashtagResult = insertHashtags(story, storyId);

        // 스토리 아이디 태그 넣기
        int tagResult = insertTags(story, storyId);
        return imageResult * hashtagResult * tagResult;
    }

    private int insertStory(StoryRequestDto.Create story, int userId) {
        // 스토리 생성
        StoryRequestDto.CreateStory newStory = new StoryRequestDto.CreateStory(
                userId,
                story.getContent(),
                story.getWeather(),
                story.getEmotion(),
                story.getLocation(),
                story.getAccessLevel()
        );

        storyMapper.insertStoryByStory(newStory);
        return newStory.getStoryId();
    }

    private int insertStoryImage(StoryRequestDto.Create story, int storyId) throws IOException {
        MultipartFile[] files = story.getFiles();

        if (files.length > 0 && !files[0].isEmpty()) {

            String realPath =  "/story";
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + today;
            File folder = new File(saveFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            List<FileInfoDto> fileInfos = new ArrayList();
            for (MultipartFile mfile: files) {
                FileInfoDto fileInfoDto = new FileInfoDto();
                String originalFileName = mfile.getOriginalFilename();
                if (!originalFileName.isEmpty()) {
                    String saveFileName = UUID.randomUUID().toString()
                            + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    fileInfoDto.setSaveFolder(today);
                    fileInfoDto.setOriginalFile(originalFileName);
                    fileInfoDto.setSaveFile(saveFileName);
                    mfile.transferTo(new File(folder, saveFileName));
                }

                StoryRequestDto.CreateImage newImage = new StoryRequestDto.CreateImage(
                        storyId,
                        fileInfoDto
                );
                int result = storyMapper.insertImageByStoryImage(newImage);
                if (result != 1) return 0;
            }
        }
        return 1;
    }

    private int insertHashtags(StoryRequestDto.Create story, int storyId) {
        for (String hashtag: story.getHashtags()) {
            StoryRequestDto.CreateHashtag newHashtag = new StoryRequestDto.CreateHashtag(
                    storyId,
                    hashtag
            );
            int result = storyMapper.insertHashtagByStoryHashtag(newHashtag);
            if (result != 1) return 0;
        }
        return 1;
    }

    private int insertTags(StoryRequestDto.Create story, int storyId) {
        for (int userId: story.getTags()) {
            StoryRequestDto.CreateTag newTag = new StoryRequestDto.CreateTag(
                    storyId,
                    userId
            );
            int result = storyMapper.insertTagByStoryTag(newTag);
            if (result != 1) return 0;
        }
        return 1;
    }

    @Override
    public int deleteStory(int storyId) {
        // 스토리 삭제
        int storyResult = storyMapper.deleteStoryByStoryId(storyId);
        // 스토리 이미지 삭제
        int imageResult = storyMapper.deleteImagesByStoryId(storyId);
        // 스토리 해시태그 삭제
        int hashtagResult = storyMapper.deleteHashtagsByStoryId(storyId);
        // 스토리 아이디 태그 삭제
        int tagResult = storyMapper.deleteTagsByStoryId(storyId);
        return storyResult * imageResult * hashtagResult * tagResult;
    }

    @Override
    public int updateStory(int storyId, StoryRequestDto.Update story) {
        return storyMapper.updateStoryByStory(storyId, story);
    }

    @Override
    public int likeStory(int storyId, int userId) {
        return storyMapper.insertStoryLikeByStoryIdAndUserId(storyId, userId);
    }
    @Override
    public int notlikeStory(int storyId, int userId) {
        return storyMapper.deleteStoryLikeByStoryIdAndUserId(storyId, userId);
    }

    @Override
    public List<StoryResponseDto.Story> stories(int userId) {
        List<StoryResponseDto.Story> stories = new ArrayList<>();

        // 해당 유저가 볼 수 있는 모든 스토리 id를 담은 list (stories table에서 자신이 팔로우 한 유저거나, 전체 공개 게시물)
        List<Integer> storyIdList = storyMapper.selectAllStoriesIdsByUserId(userId);
        System.out.println(storyIdList);
        for (Integer storyId: storyIdList) {
            StoryResponseDto.SelectStory story = storyMapper.selectStoryByStoryId(storyId);
            if (story == null) return null;
            StoryResponseDto.Story newStory = new StoryResponseDto.Story(
                    story.getStoryId(),
                    story.getUserId(),
                    story.getCreatedAt(),
                    story.getContent(),
                    story.getWeather(),
                    story.getEmotion(),
                    story.getLocation(),
                    story.getAccessLevel(),
                    storyMapper.selectStoryTagsByStoryId(storyId),
                    storyMapper.selectStoryHashtagsByStoryId(storyId),
                    storyMapper.selectStoryImagesByStoryId(storyId),
                    storyMapper.selectStoryLikeCountByStoryId(storyId)
            );
            stories.add(newStory);
        }
        return stories;
    }

    @Override
    public StoryResponseDto.Story story(int storyId) {
        StoryResponseDto.SelectStory story = storyMapper.selectStoryByStoryId(storyId);
        if (story == null) return null;
        StoryResponseDto.Story newStory = new StoryResponseDto.Story(
                story.getStoryId(),
                story.getUserId(),
                story.getCreatedAt(),
                story.getContent(),
                story.getWeather(),
                story.getEmotion(),
                story.getLocation(),
                story.getAccessLevel(),
                storyMapper.selectStoryTagsByStoryId(storyId),
                storyMapper.selectStoryHashtagsByStoryId(storyId),
                storyMapper.selectStoryImagesByStoryId(storyId),
                storyMapper.selectStoryLikeCountByStoryId(storyId)
                );
        return newStory;
    }

    @Override
    public List<StoryResponseDto.Story> mystories(int userId) {
        List<StoryResponseDto.Story> stories = new ArrayList<>();
        List<Integer> storyIdList = storyMapper.selectMyStoriesIdsByUserId(userId);
        for (Integer storyId: storyIdList) {
            StoryResponseDto.SelectStory story = storyMapper.selectStoryByStoryId(storyId);
            if (story == null) return null;
            StoryResponseDto.Story newStory = new StoryResponseDto.Story(
                    story.getStoryId(),
                    story.getUserId(),
                    story.getCreatedAt(),
                    story.getContent(),
                    story.getWeather(),
                    story.getEmotion(),
                    story.getLocation(),
                    story.getAccessLevel(),
                    storyMapper.selectStoryTagsByStoryId(storyId),
                    storyMapper.selectStoryHashtagsByStoryId(storyId),
                    storyMapper.selectStoryImagesByStoryId(storyId),
                    storyMapper.selectStoryLikeCountByStoryId(storyId)
            );
            stories.add(newStory);
        }
        return stories;
    }
}

package com.tinyhappytrip.story.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
public class StoryResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Story {
        private int storyId;
        private int userId;
        private String createdAt;
        private String content;
        private String weather;
        private String emotion;
        private String location;
        private AccessLevel accessLevel;
        private List<String> tags;
        private List<String> tagUsers;
        private List<FileInfoDto> fileInfos;
        private int likeCount;
    }

    @Getter
    public static class SelectStory {
        private int storyId;
        private int userId;
        private String createdAt;
        private String content;
        private String weather;
        private String emotion;
        private String location;
        private AccessLevel accessLevel;
    }

}

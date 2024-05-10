package com.tinyhappytrip.story.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class StoryRequestDto {
    @Getter
    @ToString
    public static class Create {
        private String content;
        private String weather;
        private String emotion;
        private String location;
        private AccessLevel accessLevel;
        private List<String> hashtags;
        private List<Integer> tags;
        private MultipartFile[] files;
    }

    @Getter
    public static class CreateStory {
        @Setter
        private int storyId;
        private int userId;
        private String content;
        private String weather;
        private String emotion;
        private String location;
        private AccessLevel accessLevel;
        public CreateStory(int userId, String content, String weather, String emotion, String location, AccessLevel accessLevel) {
            this.userId = userId;
            this.content = content;
            this.weather = weather;
            this.emotion = emotion;
            this.location = location;
            this.accessLevel = accessLevel;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CreateImage {
        private int storyId;
        private FileInfoDto file;

    }

    @Getter
    @AllArgsConstructor
    public static class CreateHashtag {
        private int storyId;
        private String hashtag;
    }

    @Getter
    @AllArgsConstructor
    public static class CreateTag {
        private int storyId;
        private int userId;
    }

    @Setter
    @Getter
    public static class Update {
        private String content;
        private String weather;
        private String emotion;
        private String location;
        private AccessLevel accessLevel;
    }
}

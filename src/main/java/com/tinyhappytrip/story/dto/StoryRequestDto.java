package com.tinyhappytrip.story.dto;

import com.tinyhappytrip.story.domain.StoryImage;
import com.tinyhappytrip.story.domain.enums.Scope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class StoryRequestDto {
    @Getter
    @Setter
    public static class Create {
        private Long storyId;
        private String content;
        private String weather;
        private String emotion;
        private String location;
        private Scope scope;
        private double latitude;
        private double longitude;
        private List<String> hashtags;
        private List<Integer> tags;
        private MultipartFile[] images;
        private List<StoryImage> imagePaths;
    }

    @Setter
    @Getter
    public static class Update {
        private String content;
        private Scope scope;
    }

    @Getter
    @Setter
    public static class Comment {
        private Long storyReplyId;
        private String content;
    }
}

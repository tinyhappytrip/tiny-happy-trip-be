package com.tinyhappytrip.story.dto;

import com.tinyhappytrip.story.domain.Story;
import com.tinyhappytrip.story.domain.enums.Scope;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class StoryRequest {
    @Getter
    @Setter
    @Builder
    public static class CreateDto {
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
        private MultipartFile[] imageFiles;

        public Story toEntity() {
            return Story.builder()
                    .storyId(storyId)
                    .content(content)
                    .weather(weather)
                    .emotion(emotion)
                    .location(location)
                    .scope(scope)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }
    }

    @Setter
    @Getter
    public static class UpdateDto {
        private String content;
        private Scope scope;

        public Story toEntity() {
            return Story.builder()
                    .content(content)
                    .scope(scope)
                    .build();
        }
    }
}

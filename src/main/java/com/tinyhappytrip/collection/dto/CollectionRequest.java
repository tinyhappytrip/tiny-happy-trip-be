package com.tinyhappytrip.collection.dto;

import com.tinyhappytrip.collection.domain.Collection;
import com.tinyhappytrip.collection.domain.enums.Scope;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CollectionRequest {
    @Getter
    @Setter
    @ToString
    public static class CreateDto {
        private Long userId;
        private Long collectionId;
        private String title;
        private String description;
        private Scope scope;
        private List<Long> collectionItems;
        private String musicKeyword;
        private List<String> hashtags;

        public Collection toEntity() {
            return Collection.builder()
                    .userId(userId)
                    .collectionId(collectionId)
                    .title(title)
                    .description(description)
                    .scope(scope)
                    .musicKeyword(musicKeyword)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Update {
        private Long userId;
        private Long collectionId;
        private String title;
        private String description;
        private Scope scope;
        private List<Long> collectionItems;
        private String musicKeyword;
        private List<String> hashtags;
        private MultipartFile image;
    }

    @Getter
    @Setter
    public static class Comment {
        private Long collectionCommentId;
        private Long collectionId;
        private String content;
    }
}

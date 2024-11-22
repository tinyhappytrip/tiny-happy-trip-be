package com.tinyhappytrip.collection.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionComment {
    private Long collectionCommentId;
    private Long userId;
    private Long collectionId;
    private String content;
    private String createdAt;
}

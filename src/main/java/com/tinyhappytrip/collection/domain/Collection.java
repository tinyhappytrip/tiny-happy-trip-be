package com.tinyhappytrip.collection.domain;

import com.tinyhappytrip.collection.domain.enums.Scope;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Collection {
    private Long collectionId;
    private Long userId;
    private String title;
    private String description;
    private Scope scope;
    private String createdAt;
    private String musicKeyword;

    public Collection(Long userId, String title, String description, Scope scope, String musicKeyword) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.scope = scope;
        this.musicKeyword = musicKeyword;
    }
}

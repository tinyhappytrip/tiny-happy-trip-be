package com.tinyhappytrip.story.dto;

public enum AccessLevel {
    PUBLIC("전체 공개"),
    PRIVATE("비공개"),
    FOLLOWERS("팔로워만 공개");

    private String description;

    AccessLevel (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

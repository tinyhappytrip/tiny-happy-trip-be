package com.tinyhappytrip.story.domain;

import com.tinyhappytrip.story.domain.enums.Scope;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Story {
    private Long storyId;
    private Long userId;
    private String createdAt;
    private String content;
    private String weather;
    private String emotion;
    private String placeName;
    private Long placeId;
    private String roadAddressName;
    private Scope scope;
    private double latitude;
    private double longitude;
}


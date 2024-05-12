package com.tinyhappytrip.story.domain;

import com.tinyhappytrip.story.domain.enums.Scope;
import lombok.*;

@Getter
@Setter
public class Story {
    private Long storyId;
    private Long userId;
    private String createdAt;
    private String content;
    private String weather;
    private String emotion;
    private String location;
    private Scope scope;
    private double latitude;
    private double longitude;
}


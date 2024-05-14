package com.tinyhappytrip.story.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryImage {
    private Long storyImageId;
    private Long storyId;
    private String storyImageName;
    private String storyImagePath;
}

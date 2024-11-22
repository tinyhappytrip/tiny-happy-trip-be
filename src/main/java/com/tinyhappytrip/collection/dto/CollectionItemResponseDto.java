package com.tinyhappytrip.collection.dto;

import lombok.Getter;
import lombok.Setter;

public class CollectionItemResponseDto {
    @Getter
    @Setter
    public static class collectionItem {
        private int collectionItemId;
        private int collectionId;
        private int storyId;
    }
}
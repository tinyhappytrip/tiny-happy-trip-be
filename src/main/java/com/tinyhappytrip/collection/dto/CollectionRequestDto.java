package com.tinyhappytrip.collection.dto;

import lombok.Getter;
import lombok.Setter;

public class CollectionRequestDto {
    @Getter
    @Setter
    public static class CollectionItem {
        private int collectionItemId;
        private int collectionId;
        private int storyId;
    }
}

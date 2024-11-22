package com.tinyhappytrip.gotolist.dto;

import com.tinyhappytrip.gotolist.domain.GotoItem;
import com.tinyhappytrip.gotolist.domain.GotoList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class GotoResponse {

    @Getter
    @Setter
    @Builder
    public static class GotoListDto {
        private Long gotoListId;
        private String title;
        private String createAt;
        private Long count;

        public static GotoResponse.GotoListDto toResponseDto(GotoList gotoList, Long count) {
            return GotoListDto.builder()
                    .gotoListId(gotoList.getGotoListId())
                    .title(gotoList.getTitle())
                    .createAt(gotoList.getCreatedAt())
                    .count(count)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class GotoItemDto {
        private Long gotoItemId;
        private Long gotoListId;
        private String title;
        private String placeName;
        private String description;
        private Long placeId;
        private String placeImage;
        private Double latitude;
        private Double longitude;

        public static GotoItemDto toResponseDto(GotoItem gotoItem, String title) {
            return GotoItemDto.builder()
                    .gotoItemId(gotoItem.getGotoItemId())
                    .gotoListId(gotoItem.getGotoListId())
                    .title(title)
                    .placeName(gotoItem.getPlaceName())
                    .description(gotoItem.getDescription())
                    .placeId(gotoItem.getPlaceId())
                    .placeImage(gotoItem.getPlaceImage())
                    .latitude(gotoItem.getLatitude())
                    .longitude(gotoItem.getLongitude())
                    .build();
        }
    }
}

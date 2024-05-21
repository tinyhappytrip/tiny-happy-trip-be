package com.tinyhappytrip.gotolist.dto;

import com.tinyhappytrip.gotolist.domain.GotoItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class GotoRequest {

    @Getter
    @Setter
    @Builder
    public static class CreateItemDto {
        private Long gotoListId;
        private String placeName;
        private String description;
        private Long placeId;
        private String placeImage;
        private Double latitude;
        private Double longitude;
        private MultipartFile imageFile;

        public GotoItem toEntity() {
            return GotoItem.builder()
                    .gotoListId(gotoListId)
                    .placeName(placeName)
                    .description(description)
                    .placeId(placeId)
                    .placeImage(placeImage)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }
    }
}

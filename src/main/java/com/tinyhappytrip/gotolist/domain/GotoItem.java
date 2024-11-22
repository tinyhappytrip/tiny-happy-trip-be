package com.tinyhappytrip.gotolist.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GotoItem {
    private Long gotoItemId;
    private Long gotoListId;
    private String placeName;
    private String description;
    private Long placeId;
    private String placeImage;
    private Double latitude;
    private Double longitude;
}

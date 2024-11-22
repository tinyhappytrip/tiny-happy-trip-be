package com.tinyhappytrip.gotolist.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GotoList {
    private Long gotoListId;
    private String title;
    private Long userId;
    private String createdAt;
}
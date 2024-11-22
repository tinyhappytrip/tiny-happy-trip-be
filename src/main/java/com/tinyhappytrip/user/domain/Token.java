package com.tinyhappytrip.user.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Token {
    private Long tokenId;
    private Long userId;
    private String refreshToken;
    private String expiresAt;
}

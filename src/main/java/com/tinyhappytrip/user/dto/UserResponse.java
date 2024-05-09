package com.tinyhappytrip.user.dto;

import com.tinyhappytrip.user.SocialType;
import com.tinyhappytrip.user.domain.User;
import lombok.*;

import java.util.List;

public class UserResponse {

    @Getter
    @Setter
    @Builder
    @ToString
    public static class UserInfo {
        private Long userId;
        private String email;
        private String nickname;
        private String profileImage;
        private String introduction;
        private SocialType socialType;
        private int followerCount;
        private int followingCount;

        public static UserInfo from(User user) {
            return UserInfo.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .profileImage(user.getProfileImage())
                    .introduction(user.getIntroduction())
                    .socialType(user.getSocialType())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Follow {
        private List<Long> userId;
    }

    @Getter
    @Setter
    public static class Followee {
    }
}

package com.tinyhappytrip.user.dto;

import com.tinyhappytrip.user.domain.enums.SocialType;
import com.tinyhappytrip.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserResponse {

    @Getter
    @Setter
    @Builder
    public static class UserInfo {
        private Long userId;
        private String email;
        private String nickname;
        private String profileImageName;
        private String introduction;
        private SocialType socialType;
        private int followerCount;
        private int followingCount;

        public static UserInfo from(User user) {
            return UserInfo.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .profileImageName(user.getProfileImageName())
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

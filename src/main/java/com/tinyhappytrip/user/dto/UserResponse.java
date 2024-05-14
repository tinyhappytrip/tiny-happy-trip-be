package com.tinyhappytrip.user.dto;

import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.domain.enums.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserResponse {

    @Getter
    @Setter
    @Builder
    public static class UserDto {
        private Long userId;
        private String email;
        private String nickname;
        private String userImage;
        private String introduction;
        private SocialType socialType;
        private Long followerCount;
        private Long followingCount;

        public static UserDto toUserDto(User user, Long followerCount, Long followingCount) {
            return UserDto.builder()
                    .userId(user.getUserId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .userImage(user.getUserImage())
                    .introduction(user.getIntroduction())
                    .socialType(user.getSocialType())
                    .followerCount(followerCount)
                    .followingCount(followingCount)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class FollowUserDto {
        private Long userId;
        private String nickname;
        private String userImage;

        public static FollowUserDto toUserDto(User user) {
            return FollowUserDto.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .userImage(user.getUserImage())
                    .build();
        }
    }
}

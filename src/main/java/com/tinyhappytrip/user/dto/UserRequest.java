package com.tinyhappytrip.user.dto;

import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.domain.enums.SocialType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

public class UserRequest {
    @Setter
    @Getter
    public static class LoginDto {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class JoinDto {
        private String email;
        private String password;
        private String nickname;
        private SocialType socialType = SocialType.EMAIL;
        private String userImage;

        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .socialType(socialType)
                    .userImage(userImage)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class EditDto {
        private Long userId;
        private String password;
        private String nickname;
        private String introduction;

        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .password(password)
                    .nickname(nickname)
                    .introduction(introduction)
                    .build();
        }
    }
}


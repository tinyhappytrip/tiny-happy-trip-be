package com.tinyhappytrip.user.dto;

import com.tinyhappytrip.user.domain.enums.SocialType;
import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @Setter
    @Getter
    public static class Login {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class Join {
        private String email;
        private String password;
        private String birthDate;
        private String nickname;
        private SocialType socialType = SocialType.EMAIL;
        private String profileImageName = "default.jpg";
        private String profileImagePath = "C:\\tinyhappytrip\\user\\default.jpg";
    }

    @Getter
    @Setter
    public static class Edit {
        private Long userId;
        private String password;
        private String nickname;
        private String introduction;
    }

    @Getter
    @Setter
    public static class Follow {
        private Long followerId;
        private Long followeeId;
    }
}


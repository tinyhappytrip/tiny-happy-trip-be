package com.tinyhappytrip.user.dto;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @Setter
    @Getter
    public static class Login {
        private String email;
        private String password;
    }
}

package com.tinyhappytrip.smtp.dto;

import lombok.Getter;
import lombok.Setter;

public class EmailRequestDto {
    @Getter
    @Setter
    public static class SignUp {
        private String email;
    }
}

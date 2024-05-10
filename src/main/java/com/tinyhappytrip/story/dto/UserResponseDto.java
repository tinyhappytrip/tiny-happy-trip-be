package com.tinyhappytrip.story.dto;

import lombok.Getter;
import lombok.Setter;

public class UserResponseDto {

    @Getter
    @Setter
    public static class Nickname {
        private int userId;
        private String nickname;
    }
}

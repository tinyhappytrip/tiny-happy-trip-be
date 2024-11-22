package com.tinyhappytrip.chat.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoom {
    private Long chatRoomId;
    private Long participantId1;
    private Long participantId2;
}
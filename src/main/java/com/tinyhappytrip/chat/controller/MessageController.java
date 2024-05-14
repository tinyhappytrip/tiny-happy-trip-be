package com.tinyhappytrip.chat.controller;

import com.tinyhappytrip.chat.dto.ChatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chatroom")
    public void sendMessage(ChatRequest.ChatDto chat) {
        sendingOperations.convertAndSend("/sub/chatroom/" + chat.getChatRoomId(), chat);
        log.info("{}", "메세지 요청 성공");
    }
}

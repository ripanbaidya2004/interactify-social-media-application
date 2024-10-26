package com.ripan.production.controller;

import com.ripan.production.model.Message;
import com.ripan.production.model.User;
import com.ripan.production.service.MessageService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @PostMapping("/messages/chat/{chatId}")
    public Message createMessage(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId, @RequestBody Message message) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return messageService.createMessage(user, chatId, message);
    }

    @GetMapping("/messages/chat/{chatId}")
    public List<Message> findChatMessage(@RequestHeader("Authorization") String jwt, @PathVariable Integer chatId) throws Exception {
        User user = userService.findUserByJwt(jwt);
        return messageService.findChatMessages(chatId);
    }
}

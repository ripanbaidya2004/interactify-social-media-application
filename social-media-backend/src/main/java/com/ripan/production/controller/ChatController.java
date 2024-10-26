package com.ripan.production.controller;

import com.ripan.production.model.Chat;
import com.ripan.production.model.User;
import com.ripan.production.request.CreateChatRequest;
import com.ripan.production.service.ChatService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @PostMapping("/chats")
    public Chat createChat(@RequestBody CreateChatRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        User user2 = userService.findUserById(request.getUserId());

        return chatService.createChat(reqUser, user2);
    }

    @GetMapping("/chats")
    public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        return chatService.findUsersChat(reqUser.getId());
    }

    @GetMapping("/chats/{chatId}")
    public Chat findChatById(@PathVariable Integer chatId) throws Exception {
        return chatService.findChatById(chatId);
    }
}

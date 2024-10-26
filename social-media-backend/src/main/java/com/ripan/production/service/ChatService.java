package com.ripan.production.service;

import com.ripan.production.model.Chat;
import com.ripan.production.model.User;

import java.util.List;

public interface ChatService {

    Chat createChat(User reqUser, User user);

    Chat findChatById(Integer chatId) throws Exception;

    List<Chat> findUsersChat(Integer userId);
}

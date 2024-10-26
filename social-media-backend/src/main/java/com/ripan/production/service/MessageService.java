package com.ripan.production.service;

import com.ripan.production.model.Message;
import com.ripan.production.model.User;

import java.util.List;

public interface MessageService {

    Message createMessage(User user, Integer chatId, Message message) throws Exception;

    List<Message> findChatMessages(Integer chatId) throws Exception;
}

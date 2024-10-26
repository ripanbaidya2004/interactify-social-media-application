package com.ripan.production.service;

import com.ripan.production.model.Chat;
import com.ripan.production.model.Message;
import com.ripan.production.model.User;
import com.ripan.production.repository.ChatRepository;
import com.ripan.production.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final ChatRepository chatRepository;

    @Override public Message createMessage(User user, Integer chatId, Message message) throws Exception {
        Chat chat = chatService.findChatById(chatId);
        Message createMessageRequest = new Message();

        createMessageRequest.setChat(chat);
        createMessageRequest.setUser(user);
        createMessageRequest.setContent(message.getContent());
        createMessageRequest.setImage(message.getImage());
        createMessageRequest.setTimeStamp(LocalDateTime.now());

        Message persistedMessage = messageRepository.save(createMessageRequest);
        chat.getMessages().add(persistedMessage);
        chatRepository.save(chat);

        return persistedMessage;
    }

    @Override public List<Message> findChatMessages(Integer chatId) throws Exception {
        Chat chat = chatService.findChatById(chatId);
        if(chat == null) throw new Exception("chat not found with id"+chatId);
        return messageRepository.findByChatId(chat.getId());
    }
}

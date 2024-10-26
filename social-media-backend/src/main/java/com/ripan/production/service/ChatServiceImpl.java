package com.ripan.production.service;

import com.ripan.production.model.Chat;
import com.ripan.production.model.User;
import com.ripan.production.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override public Chat createChat(User reqUser, User user) {
        Chat isExist = chatRepository.findChatByUsersId(user, reqUser);
        if(isExist != null) return isExist;

        Chat chat = new Chat();
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setTimeStamp(LocalDateTime.now());

        return chatRepository.save(chat);
    }

    @Override public Chat findChatById(Integer chatId) throws Exception {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if(optionalChat.isEmpty()) throw new Exception("chat not found with id"+chatId);
        return optionalChat.get();
    }

    @Override public List<Chat> findUsersChat(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }
}

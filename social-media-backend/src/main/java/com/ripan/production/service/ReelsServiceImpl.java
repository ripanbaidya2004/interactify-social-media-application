package com.ripan.production.service;

import com.ripan.production.model.Reels;
import com.ripan.production.model.User;
import com.ripan.production.repository.ReelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReelsServiceImpl implements ReelsService{

    private final ReelsRepository repository;
    private final UserService userService;
    private final ReelsRepository reelsRepository;

    @Override public Reels createReels(Reels reels, User user) {
        Reels createdReelsRequest = new Reels();

        createdReelsRequest.setUser(user);
        createdReelsRequest.setTitle(reels.getTitle());
        createdReelsRequest.setVideo(reels.getVideo());
        createdReelsRequest.setCreatedAt(LocalDateTime.now());

        return reelsRepository.save(createdReelsRequest);
    }

    @Override public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    @Override
    public List<Reels> findReelsByUserId(Integer userId) throws Exception {
        User user = userService.findUserById(userId);

        if (user == null)   throw new Exception("User not found for userId: " + userId);
        List<Reels> reelsList = reelsRepository.findByUserId(userId);

        return (reelsList != null) ? reelsList : Collections.emptyList();
    }
}

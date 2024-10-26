package com.ripan.production.service;

import com.ripan.production.model.Reels;
import com.ripan.production.model.User;

import java.util.List;

public interface ReelsService {

    Reels createReels(Reels reels, User user);

    List<Reels> findAllReels();

    List<Reels> findReelsByUserId(Integer userId) throws Exception;

}

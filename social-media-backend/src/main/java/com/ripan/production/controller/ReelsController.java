package com.ripan.production.controller;

import com.ripan.production.model.Reels;
import com.ripan.production.model.User;
import com.ripan.production.service.ReelsService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReelsController {

    private final ReelsService reelsService;
    private final UserService userService;

    @PostMapping("/reels")
    public Reels createReels(@RequestBody Reels reels, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        return reelsService.createReels(reels, reqUser);
    }

    @GetMapping("/reels")
    public List<Reels> findAllReels(){
        return reelsService.findAllReels();
    }

    @GetMapping("/reels/user/{userId}")
    public List<Reels> findReelsByUserId(@PathVariable Integer userId) throws Exception {
        return reelsService.findReelsByUserId(userId);
    }
}

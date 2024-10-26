package com.ripan.production.controller;

import com.ripan.production.config.JwtProvider;
import com.ripan.production.model.User;
import com.ripan.production.repository.UserRepository;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable("userId")Integer id) throws Exception{
        User user = userService.findUserById(id);
        return user;
    }


    @PutMapping("/users")
    public User updateUserDetails(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        User updatedUser = userService.updateUser(user, reqUser.getId()); // step4. save the updated information to the database.

        return updatedUser;
    }

    @PutMapping("/users/follow/{userId2}")
    public User followUserHandler(@RequestHeader ("Authorization") String jwt, @PathVariable Integer userId2) throws Exception{
        User reqUser = userService.findUserByJwt(jwt);
        User followedUser = userService.followUser(reqUser.getId(), userId2);

        return followedUser;
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query){
        List<User> searchedUsers = userService.searchUser(query);

        return searchedUsers;
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable Integer userId) throws Exception{
        String deletedUser = userService.deleteUser(userId);

        return deletedUser;
    }

//    @GetMapping("/users/profile")
//    public User getUserFromToken(@RequestHeader("Authorization") String jwt) throws Exception {
//        User user = userService.findUserByJwt(jwt);
//        user.setPassword(null);
//        return user;
//    }
    @GetMapping("/users/profile")
    public ResponseEntity<User> getUserFromToken(@RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

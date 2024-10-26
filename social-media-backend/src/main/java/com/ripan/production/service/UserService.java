package com.ripan.production.service;

import com.ripan.production.model.User;

import java.util.List;

public interface UserService {

    public User registerUser(User user);

    public User findUserById(Integer userId) throws Exception;

    public User findUserByEmail(String email) throws Exception;

    public User followUser(Integer userId1, Integer userId2) throws Exception; // userId1 wants to follow userId2

    public User updateUser(User user, Integer userId) throws Exception;

    public List<User> searchUser(String query);

    public String deleteUser(Integer userId) throws Exception;

    public User findUserByJwt(String jwt) throws Exception;
}

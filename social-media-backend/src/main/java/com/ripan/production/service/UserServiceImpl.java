package com.ripan.production.service;

import com.ripan.production.config.JwtProvider;
import com.ripan.production.model.User;
import com.ripan.production.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User createUserRequest = new User();

        createUserRequest.setFirstName(user.getFirstName());
        createUserRequest.setLastName(user.getLastName());
        createUserRequest.setEmail(user.getEmail());
        createUserRequest.setPassword(user.getPassword());
        createUserRequest.setGender(user.getGender());

        User persistedUser = userRepository.save(createUserRequest);

        return persistedUser;
    }

    @Override
    public User findUserById(Integer userId) throws Exception{
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) throw new Exception("user not found with id " + userId);

        return optionalUser.get();
    }

    @Override
    public User findUserByEmail(String email) throws Exception{
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("user not found with email " + email);
        }
        return user;
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws Exception {
        // Note: reqUserId wants to follow userId2
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);

        user2.getFollowers().add(reqUser.getId()); // the followers of user2 will increased
        reqUser.getFollowing().add(user2.getId()); // the following of reqUser will increased

        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;
    }

    @Override
    public User updateUser(User user, Integer userId) throws Exception {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new Exception("user not found with id " + userId);

        User existingUser = optionalUser.get();

        if(user.getFirstName() != null)
            existingUser.setFirstName(user.getFirstName());
        if(user.getLastName() != null)
            existingUser.setLastName(user.getLastName());
        if(user.getEmail() != null)
            existingUser.setEmail(user.getEmail());
        if(user.getGender() != null)
            existingUser.setGender(user.getGender());

        User persistedUser = userRepository.save(existingUser);

        return persistedUser;
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users;
    }

    @Override
    public String deleteUser(Integer userId) throws Exception{
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new Exception("user not found with id " + userId);

        userRepository.delete(optionalUser.get());
        return "user deleted successfully with id " + userId;
    }


    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        if (email == null || email.trim().isEmpty()) throw new Exception("Invalid token: Unable to extract email");

        // Log the email for debug purposes
        System.out.println("Email extracted from JWT: " + email);

        User user = userRepository.findByEmail(email);
        if (user == null) throw new Exception("User not found for the email: " + email);

        return user;
    }
    /*
    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromJwtToken(jwt);

        User user = userRepository.findByEmail(email);
        return user;
    }
    */
}

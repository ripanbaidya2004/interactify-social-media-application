package com.ripan.production.controller;

import com.ripan.production.config.JwtProvider;
import com.ripan.production.model.User;
import com.ripan.production.repository.UserRepository;
import com.ripan.production.request.LoginRequest;
import com.ripan.production.response.AuthResponse;
import com.ripan.production.service.CustomUserDetailsService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception {

        User isExist = userRepository.findByEmail(user.getEmail());

        if(isExist != null){
            throw new Exception("User already exist with email " + user.getEmail());
        }

        User createUserRequest = new User();

        createUserRequest.setFirstName(user.getFirstName());
        createUserRequest.setLastName(user.getLastName());
        createUserRequest.setEmail(user.getEmail());
        createUserRequest.setPassword(passwordEncoder.encode(user.getPassword()));
        createUserRequest.setGender(user.getGender());

        User persistedUser = userRepository.save(createUserRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken(persistedUser, persistedUser);
        String token = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(token, "User Created Successfully");

        return response;
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String token = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(token, "User login Successfully");
        return response;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(userDetails == null)
            throw new BadCredentialsException("Invalid email");

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

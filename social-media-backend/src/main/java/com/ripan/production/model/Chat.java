package com.ripan.production.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String chatName;

    private String chatImage;

    @ManyToMany
    private List<User> users = new ArrayList<>(); // would have two user. the user log in. the user with try to chat

    @OneToMany(mappedBy = "chat")  // it will create only one table
    private List<Message> messages = new ArrayList<>();

    private LocalDateTime timeStamp;
}

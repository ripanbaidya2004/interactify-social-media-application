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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;  // actual Comment

    @ManyToOne
    private User user;

    @ManyToMany
    private List<User> liked = new ArrayList<>();

    private LocalDateTime createdAt;

}

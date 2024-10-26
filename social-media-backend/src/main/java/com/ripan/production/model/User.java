package com.ripan.production.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String gender;

    private List<Integer> followers = new ArrayList<>();

    private List<Integer> following = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Post> savedPost = new ArrayList<>();

}

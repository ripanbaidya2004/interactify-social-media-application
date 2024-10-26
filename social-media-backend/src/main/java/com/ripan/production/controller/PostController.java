package com.ripan.production.controller;

import com.ripan.production.model.Post;
import com.ripan.production.model.User;
import com.ripan.production.response.ApiResponse;
import com.ripan.production.service.PostService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt, @RequestBody Post post) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post createdPost = postService.createPost(post, reqUser.getId());

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);

        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse response = new ApiResponse(message, true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);

        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Post>> findPostByUserId(@PathVariable Integer userId) throws Exception {
        List<Post> posts = postService.findPostByUserId(userId);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts")
    public List<Post> findAllPosts(){
        return postService.findAllPosts();
    }

    @PutMapping("/posts/save/{postId}")
    public ResponseEntity<Post> savedPostHandler(@PathVariable Integer postId, @RequestHeader("Authorization")String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post savedPost = postService.savedPost(postId, reqUser.getId());

        return new ResponseEntity<>(savedPost, HttpStatus.ACCEPTED);
    }

    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId, @RequestHeader("Authorization")String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post likedPost = postService.likePost(postId, reqUser.getId());

        return new ResponseEntity<>(likedPost, HttpStatus.ACCEPTED);
    }
}


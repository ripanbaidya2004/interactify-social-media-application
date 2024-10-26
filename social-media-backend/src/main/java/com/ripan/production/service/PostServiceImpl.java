package com.ripan.production.service;

import com.ripan.production.model.Post;
import com.ripan.production.model.User;
import com.ripan.production.repository.PostRepository;
import com.ripan.production.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public Post createPost(Post post, Integer userId) throws Exception {

        User user = userService.findUserById(userId);
        Post createdPostRequest = new Post();

        createdPostRequest.setCaption(post.getCaption());
        createdPostRequest.setImage(post.getImage());
        createdPostRequest.setVideo(post.getVideo());
        createdPostRequest.setCreatedAt(LocalDateTime.now());
        createdPostRequest.setUser(user);

        Post persistedPost = postRepository.save(createdPostRequest);

        return persistedPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        /**
         * at first we'll implement the findPostById then.
         *
         */
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(post.getUser().getId() != user.getId()){
            throw new Exception("user not authorized to delete this post");
        }
        postRepository.delete(post);
        return "Post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws Exception {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()){
            throw new Exception("post not found with id"+postId);
        }
        return optionalPost.get();
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post);
        } else{
            user.getSavedPost().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        // checking if user alreday liked, then unlike the post or else like.
        if(post.getLiked().contains(user)){
            post.getLiked().remove(user);
        } else{
            post.getLiked().add(user);
        }
        return postRepository.save(post);
    }
}

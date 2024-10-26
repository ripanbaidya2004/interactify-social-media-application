package com.ripan.production.service;

import com.ripan.production.model.Comment;
import com.ripan.production.model.Post;
import com.ripan.production.model.User;
import com.ripan.production.repository.CommentRepository;
import com.ripan.production.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final PostRepository postRepository;

    @Override public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        Comment createdCommentRequest = new Comment();

        createdCommentRequest.setUser(user);
        createdCommentRequest.setContent(comment.getContent());
        createdCommentRequest.setCreatedAt(LocalDateTime.now());

        Comment persistedComment = commentRepository.save(createdCommentRequest);
        post.getComments().add(persistedComment);
        postRepository.save(post);

        return persistedComment;
    }

    @Override public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if(optionalComment.isEmpty()){
            throw new Exception("Comment not found with id"+commentId);
        }
        return optionalComment.get();
    }

    @Override public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);

        if(comment.getLiked().contains(user)){
            comment.getLiked().remove(user);
        }else{
            comment.getLiked().add(user);
        }
        return commentRepository.save(comment);
    }

    @Override public String deleteComment(Integer commentId, Integer userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);

        /* * if the user trying to delete the comment is not the owner of the comment then we'll throw an exception */
        if(!Objects.equals(comment.getUser().getId(), user.getId())){
            throw new Exception("user not authorized to delete this comment");
        }
        commentRepository.delete(comment);

        return "Comment Deleted Successfully!";
    }
}

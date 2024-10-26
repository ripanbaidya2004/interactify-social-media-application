package com.ripan.production.controller;

import com.ripan.production.model.Comment;
import com.ripan.production.model.User;
import com.ripan.production.response.DeleteResponse;
import com.ripan.production.service.CommentService;
import com.ripan.production.service.PostService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/comments/post/{postId}")
    public Comment createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt, @PathVariable Integer postId) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Comment createdComment = commentService.createComment(comment, postId, reqUser.getId());

        return createdComment;
    }

    @PutMapping("/comments/like/{commentId}")
    public Comment likeComment(@PathVariable Integer commentId, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Comment likedComment = commentService.likeComment(commentId, reqUser.getId());

        return likedComment;
    }

    @GetMapping("/comments/{commentId}")
    public Comment findCommentById(@PathVariable Integer commentId) throws Exception {
        return commentService.findCommentById(commentId);
    }


    /**
     * deleteMapping-> this method will not work, since there is some foreign key constraint
     * @param commentId
     * @param jwt
     * @return DeleteResponse object
     * @throws Exception
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<DeleteResponse> deleteMapping(@PathVariable Integer commentId, @RequestHeader("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);

        String message = commentService.deleteComment(commentId, reqUser.getId());
        DeleteResponse response = new DeleteResponse(message, true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

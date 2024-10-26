package com.ripan.production.repository;

import com.ripan.production.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findPostByUserId(@Param("userId")Integer userId);

}

package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.social.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("select p from Post p where p.user.user_id=:user_id")
	List<Post> findPostByUserId(@Param("user_id") Integer user_id);
}

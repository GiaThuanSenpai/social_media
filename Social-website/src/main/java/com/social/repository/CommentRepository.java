package com.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}

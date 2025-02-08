package com.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Comment;
import com.social.models.User;
import com.social.service.ICommentService;
import com.social.service.IUserService;

@RestController
public class CommentController {

	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/api/comments/post/{post_id}")
	public Comment createComment(@RequestBody Comment comment,
			@RequestHeader("Authorization") String jwt,
			@PathVariable("post_id") Integer post_id) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		Comment createdComment = commentService.createComment(comment, post_id, user.getUser_id());
		return createdComment;
		
	}
	
	@PutMapping("/api/comments/like/{cmt_id}")
	public Comment likeComment(
			@RequestHeader("Authorization") String jwt,
			@PathVariable("cmt_id") Integer cmt_id) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		Comment likedComment = commentService.likeComment(cmt_id, user.getUser_id());
		return likedComment;
		
	}
}

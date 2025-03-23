package com.social.service;

import com.social.models.Comment;

public interface ICommentService {

	public Comment createComment(
			Comment comment, 
			Integer post_id, 
			Integer user_id) throws Exception;
	
	public Comment findCommentById(Integer cmt_id) throws Exception;
	public Comment likeComment(Integer cmt_id, Integer user_id) throws Exception;
	
	
}

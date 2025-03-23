package com.social.service;

import java.util.List;

import com.social.models.Post;

public interface IPostService {
	Post createNewPost(Post post, Integer user_id) throws Exception;
	
	boolean deletePost(Integer post_id, Integer user_id) throws Exception;
	
	List<Post> findPostByUserId(Integer user_id);

	Post findPostById(Integer post_id) throws Exception;
	
	List<Post> findAllPost();
	
	Post savedPost(Integer post_id, Integer user_id) throws Exception;
	
	Post likePost(Integer post_id, Integer user_id) throws Exception;
}

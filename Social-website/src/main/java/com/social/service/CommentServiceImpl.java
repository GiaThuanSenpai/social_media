package com.social.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Comment;
import com.social.models.Post;
import com.social.models.User;
import com.social.repository.CommentRepository;
import com.social.repository.PostRepository;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private IPostService postService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public Comment createComment(Comment comment, Integer post_id, Integer user_id) throws Exception {
		
		User user = userService.findUserById(user_id);
		Post post = postService.findPostById(post_id);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment savedComment = commentRepository.save(comment);
		
		post.getComments().add(savedComment);
		
		postRepository.save(post);
		return savedComment;
	}

	@Override
	public Comment findCommentById(Integer cmt_id) throws Exception {
		Optional<Comment> opt = commentRepository.findById(cmt_id);
		
		if(opt.isEmpty()) throw new Exception("Comment is not exist");
		
		return opt.get();
	}

	@Override
	public Comment likeComment(Integer cmt_id, Integer user_id) throws Exception {
		Comment comment = findCommentById(cmt_id);
		User user = userService.findUserById(user_id);
		
		if(!comment.getLiked().contains(user)) comment.getLiked().add(user);
		else comment.getLiked().remove(user);
		
		return commentRepository.save(comment);
	}

}

package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.social.models.Post;
import com.social.models.User;
import com.social.service.IUserService;
import com.social.service.PostServiceImpl;

@RestController
public class PostController {

	@Autowired
	private PostServiceImpl postService;

	@Autowired
	IUserService userService;

	@PostMapping("/api/posts")
	public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt, @RequestBody Post post)
			throws Exception {

		User reqUser = userService.findUserByJwt(jwt);
		Post isCreated = postService.createNewPost(post, reqUser.getUser_id());
		return new ResponseEntity<>(isCreated, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/api/posts/{post_id}")
	public ResponseEntity<String> deletePost(@PathVariable("post_id") Integer post_id,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User reqUser = userService.findUserByJwt(jwt);
		try {
			boolean isDeleted = postService.deletePost(post_id, reqUser.getUser_id());
			if (isDeleted) {
				return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Post deletion failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/api/posts/{post_id}")
	public ResponseEntity<Post> findPostById(@PathVariable("post_id") Integer post_id) {
		try {
			Post post = postService.findPostById(post_id);
			if (post != null) {
				return new ResponseEntity<>(post, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/posts/user/{user_id}")
	public ResponseEntity<List<Post>> findUsersPost(@PathVariable("user_id") Integer user_id) {
		List<Post> posts = postService.findPostByUserId(user_id);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@GetMapping("/api/posts")
	public ResponseEntity<List<Post>> findAllPost() {
		List<Post> posts = postService.findAllPost();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	@PutMapping("/api/posts/save/{post_id}")
	public ResponseEntity<Post> savedPost(@PathVariable("post_id") Integer post_id,
			@RequestHeader("Authorization") String jwt) {

		User reqUser = userService.findUserByJwt(jwt);
		try {
			Post post = postService.savedPost(post_id, reqUser.getUser_id());
			if (post != null) {
				return new ResponseEntity<>(post, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/api/posts/like/{post_id}")
	public ResponseEntity<Post> likePost(@PathVariable("post_id") Integer post_id,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User reqUser = userService.findUserByJwt(jwt);
		Post post = postService.likePost(post_id, reqUser.getUser_id());
		
		return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
				
	}
}

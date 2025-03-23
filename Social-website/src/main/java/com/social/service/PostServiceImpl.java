package com.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Post;
import com.social.models.User;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	IUserService userService;

	@Autowired
	UserRepository userRepository;

	@Override
	public Post createNewPost(Post post, Integer user_id) throws Exception {
		User user = userService.findUserById(user_id);
		Post newPost = new Post();
		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setVideo(post.getVideo());
		newPost.setUser(user);
		newPost.setCreatedAt(LocalDateTime.now());
		return postRepository.save(newPost);
	}

	@Override
	public boolean deletePost(Integer post_id, Integer user_id) throws Exception {
		Post post = findPostById(post_id);
		User user = userService.findUserById(user_id);

		if (post.getUser().getUser_id() != user.getUser_id()) {
			throw new Exception("You can't delete another users post");
		}

		postRepository.delete(post);
		return true;
	}

	@Override
	public List<Post> findPostByUserId(Integer user_id) {

		return postRepository.findPostByUserId(user_id);
	}

	@Override
	public Post findPostById(Integer post_id) throws Exception {
		Optional<Post> opt = postRepository.findById(post_id);
		if (opt.isEmpty())
			throw new Exception("Post not found with id " + post_id);
		return opt.get();
	}

	@Override
	public List<Post> findAllPost() {
		return postRepository.findAll();
	}

	@Override
	public Post savedPost(Integer post_id, Integer user_id) throws Exception {
		Post post = findPostById(post_id);
		User user = userService.findUserById(user_id);

		if (user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		} else {
			user.getSavedPost().add(post);
		}
		userRepository.save(user);
		return post;

	}

	@Override
	public Post likePost(Integer post_id, Integer user_id) throws Exception {
		Post post = findPostById(post_id);
		User user = userService.findUserById(user_id);

		if (post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		} else {
			post.getLiked().add(user);
		}
		return postRepository.save(post);
	}

}

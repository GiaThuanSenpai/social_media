package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Story;
import com.social.models.User;
import com.social.service.IStoryService;
import com.social.service.IUserService;

@RestController
public class StoryController {
	
	@Autowired
	private IStoryService storyService;
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/api/story")
	public Story createStory(@RequestHeader("Authorization") String jwt, @RequestBody Story story) {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		Story createStory = storyService.createStory(story, reqUser);
		
		return createStory;
	}
	
	@GetMapping("/api/story/user/{user_id}")
	public List<Story> findStoryByUserId(
			@RequestHeader("Authorization") String jwt,
			@PathVariable("user_id") Integer user_id) throws Exception {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		List<Story> stories = storyService.findStoryByUserId(user_id);
		
		return stories;
	}
}

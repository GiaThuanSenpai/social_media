package com.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Story;
import com.social.models.User;
import com.social.repository.StoryRepository;

@Service
public class StoryServiceImpl implements IStoryService {

	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private IUserService userService;
	
	
	@Override
	public Story createStory(Story story, User user) {
		Story createdStory = new Story();
		
		createdStory.setCaptions(story.getCaptions());
		createdStory.setImage(story.getImage());
		createdStory.setUser(user);
		createdStory.setTimestamp(LocalDateTime.now());
		
		return storyRepository.save(createdStory);
	}

	@Override
	public List<Story> findStoryByUserId(Integer user_id) throws Exception {
//		User user = userService.findUserById(user_id);
		
		return storyRepository.findStoryByUserId(user_id);
	}

}

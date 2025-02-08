package com.social.service;

import java.util.List;

import com.social.models.Story;
import com.social.models.User;

public interface IStoryService {
	
	public Story createStory (Story story, User user);
	
	public List<Story> findStoryByUserId(Integer user_id) throws Exception;
}

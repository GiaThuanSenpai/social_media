package com.social.service;

import java.util.List;

import com.social.models.Chat;
import com.social.models.User;

public interface IChatService {

	public Chat createChat(User reqUser, User user2);
	
	public Chat findChatById(Integer chat_id) throws Exception;
	
	public List<Chat> findUsersChat(Integer user_id);
}

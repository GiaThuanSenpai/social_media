package com.social.service;

import java.util.List;

import com.social.models.Chat;
import com.social.models.Message;
import com.social.models.User;

public interface IMessageService {

	public Message createMessage(User user, Integer chat_id, Message req) throws Exception;
	
	public List<Message> findChatMessages(Integer chat_id) throws Exception;
}

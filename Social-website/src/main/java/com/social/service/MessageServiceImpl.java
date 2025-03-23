package com.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Chat;
import com.social.models.Message;
import com.social.models.User;
import com.social.repository.ChatRepository;
import com.social.repository.MessageRepository;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired 
	MessageRepository messageRepository;
	
	
	@Autowired
	IChatService chatService;
	
	@Autowired
	ChatRepository chatRepository;
	
	@Override
	public Message createMessage(User user, Integer chat_id, Message req) throws Exception {
		
		Message message = new Message();
		
		Chat chat = chatService.findChatById(chat_id);
		message.setChat(chat);
		message.setContent(req.getContent());
		message.setImage(req.getImage());
		message.setUser(user);
		message.setTimestamp(LocalDateTime.now());
		
		Message savedMessage = messageRepository.save(message);
		
		chat.getMessages().add(savedMessage);
		chatRepository.save(chat);
		return savedMessage;
	}

	@Override
	public List<Message> findChatMessages(Integer chat_id) throws Exception {
		Chat chat = chatService.findChatById(chat_id);
		return messageRepository.findByChatId(chat_id);
	}

}

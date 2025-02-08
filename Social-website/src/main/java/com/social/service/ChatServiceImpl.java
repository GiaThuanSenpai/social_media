package com.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Chat;
import com.social.models.User;
import com.social.repository.ChatRepository;

@Service
public class ChatServiceImpl implements IChatService{

	@Autowired
	private ChatRepository chatRepository;
	@Override
	public Chat createChat(User reqUser, User user2) {
		Chat isExist = chatRepository.findChatByUsersId(user2, reqUser);
		
		if(isExist != null) return isExist;
		
		Chat chat = new Chat();
		chat.getUsers().add(user2);
		chat.getUsers().add(reqUser);
		chat.setTimestamp(LocalDateTime.now());
		
		return chatRepository.save(chat);
	}

	@Override
	public Chat findChatById(Integer chat_id) throws Exception {
		Optional<Chat> opt = chatRepository.findById(chat_id);

		if(opt.isEmpty()) throw new Exception("Chat not found with id: " +  chat_id);
		
		return opt.get();
	}

	@Override
	public List<Chat> findUsersChat(Integer user_id) {
		
		return chatRepository.findByUserId(user_id);
	}

}

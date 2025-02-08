package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Message;
import com.social.models.User;
import com.social.service.IMessageService;
import com.social.service.IUserService;

@RestController
public class MessageController {

	@Autowired
	IMessageService messageService;
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/api/messages/chat/{chat_id}")
	public Message createMessage(
			@RequestBody Message req,
			@RequestHeader("Authorization") String jwt,
			@PathVariable("chat_id") Integer chat_id) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		Message message = messageService.createMessage(user, chat_id, req);
		return message;
	}
	
	@GetMapping("/api/messages/chat/{chat_id}")
	public List<Message> findChatMessage(
			@RequestHeader("Authorization") String jwt,
			@PathVariable("chat_id") Integer chat_id) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		List<Message> message = messageService.findChatMessages(chat_id);
		return message;
	}
}

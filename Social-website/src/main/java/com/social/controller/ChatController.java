package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Chat;
import com.social.models.User;
import com.social.request.CreateChatRequest;
import com.social.service.IChatService;
import com.social.service.IUserService;

@RestController
public class ChatController {

	@Autowired
	private IChatService chatService;
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/api/chats")
	public Chat createChat(@RequestHeader("Authorization") String jwt, @RequestBody CreateChatRequest req) throws Exception {
		
		User reqUser = userService.findUserByJwt(jwt);
		User user2 = userService.findUserById(req.getUser_id());
		Chat chat = chatService.createChat(reqUser, user2);
		
		return chat;
	}
	
	@GetMapping("/api/chats")
	public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt){
		
		User user = userService.findUserByJwt(jwt);
		List<Chat> chats = chatService.findUsersChat(user.getUser_id());
		
		return chats;
	}
}

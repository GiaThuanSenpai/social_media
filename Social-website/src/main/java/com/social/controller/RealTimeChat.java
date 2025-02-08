package com.social.controller;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Message;

@RestController
public class RealTimeChat {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat/{groupId}")
	public Message sendToUser(
			@Payload Message message, 
			@DestinationVariable String groupId) {
		
		simpMessagingTemplate.convertAndSendToUser(groupId, "/private", message);
		
		return message;

	}
}

package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Reels;
import com.social.models.User;
import com.social.service.IReelsService;
import com.social.service.IUserService;

@RestController
public class ReelsController {

	@Autowired
	private IReelsService reelsService;
	
	@Autowired
	private IUserService userService;
	
	
	@PostMapping("/api/reels")
	public Reels createReels(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt) {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		Reels createReels = reelsService.createReels(reel, reqUser);
		return createReels;
		
	}
	
	@GetMapping("/api/reels")
	public List<Reels> findAllReels() {	
		List<Reels> reels = reelsService.findAllReels();
		return reels;
		
	}
	
	@GetMapping("/api/reels/user/{user_id}")
	public List<Reels> findAllReels(@PathVariable Integer user_id) throws Exception {	
		List<Reels> reels = reelsService.findUsersReel(user_id);
		return reels;
		
	}
}

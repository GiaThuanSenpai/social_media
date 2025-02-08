package com.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Reels;
import com.social.models.User;
import com.social.repository.ReelsRepository;

@Service
public class ReelsServiceImpl implements IReelsService {

	
	@Autowired
	private ReelsRepository reelsRepository;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public Reels createReels(Reels reels, User user) {
		Reels createReel = new Reels();
		createReel.setTitle(reels.getTitle());
		createReel.setUser(user);
		createReel.setVideo(reels.getVideo());		
		
		return reelsRepository.save(createReel);
	}

	@Override
	public List<Reels> findAllReels() {
		// TODO Auto-generated method stub
		return reelsRepository.findAll();
	}

	@Override
	public List<Reels> findUsersReel(Integer user_id) throws Exception {
		userService.findUserById(user_id);
		return reelsRepository.findReelsByUserId(user_id);
	}

}

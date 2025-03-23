package com.social.service;

import java.util.List;

import com.social.models.Reels;
import com.social.models.User;

public interface IReelsService {
	
	public Reels createReels(Reels reels, User user);
	
	public List<Reels> findAllReels();
	
	public List<Reels> findUsersReel(Integer user_id) throws Exception;

}

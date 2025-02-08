package com.social.service;

import java.util.List;

import com.social.exceptions.UserExceptions;
import com.social.models.User;

public interface IUserService {

	public boolean registerUser(User user);
	
	public User findUserById(Integer user_id) throws UserExceptions;
	
	public User findUserByEmail(String email);
	
	public boolean deleteUser(Integer user_id) throws Exception;
	
	public User followUser(Integer user_id1, Integer user_id2) throws UserExceptions;
	
	public boolean updateUser(User user, Integer user_id) throws UserExceptions;
	
	public List<User> searchUser(String query);
	
	public User findUserByJwt(String jwt);
	
}

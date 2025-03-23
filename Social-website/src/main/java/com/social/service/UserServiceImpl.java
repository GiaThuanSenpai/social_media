package com.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.config.JwtProvider;
import com.social.exceptions.UserExceptions;
import com.social.models.User;
import com.social.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public boolean registerUser(User user) {
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		newUser.setUser_id(user.getUser_id());
		newUser.setGender(user.getGender());

		try {
			User savedUser = userRepository.save(newUser);
			return savedUser != null;
		} catch (Exception e) {
			// Log lỗi nếu cần thiết
			return false;
		}
	}

	@Override
	public User findUserById(Integer user_id) throws UserExceptions {
		Optional<User> user = userRepository.findById(user_id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserExceptions("User not exist with user_id " + user_id);
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User followUser(Integer reqUserId, Integer user_id2) throws UserExceptions {
		
		User reqUser = findUserById(reqUserId);
		User user2 = findUserById(user_id2);

		user2.getFollowers().add(reqUser.getUser_id());
		reqUser.getFollowings().add(user2.getUser_id());

		userRepository.save(reqUser);
		userRepository.save(user2);

		return reqUser;

	}

	@Override
	public boolean updateUser(User user, Integer user_id) throws UserExceptions {
		Optional<User> newUser = userRepository.findById(user_id);
		if (newUser.isEmpty()) {
			throw new UserExceptions("User not exist with id " + user_id);
		}
		User oldUser = newUser.get();

		boolean isUpdated = false;

		if (user.getFirstName() != null) {
			oldUser.setFirstName(user.getFirstName());
			isUpdated = true;
		}
		if (user.getLastName() != null) {
			oldUser.setLastName(user.getLastName());
			isUpdated = true;
		}
		if (user.getEmail() != null) {
			oldUser.setEmail(user.getEmail());
			isUpdated = true;
		}
		if (user.getPassword() != null) {
			oldUser.setPassword(user.getPassword());
			isUpdated = true;
		}
		if(user.getGender() != null) {
			oldUser.setGender(user.getGender());
			isUpdated = true;
		}
		if(user.getImgAvatar() != null) {
			oldUser.setImgAvatar(user.getImgAvatar());
			isUpdated = true;
		}
		if(user.getImgBackground() != null) {
			oldUser.setImgBackground(user.getImgBackground());
			isUpdated = true;
		}

		if (isUpdated) {
			userRepository.save(oldUser);
		}

		return isUpdated;
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepository.searchUser(query);
	}

	@Override
	public boolean deleteUser(Integer user_id) throws UserExceptions {
		Optional<User> user = userRepository.findById(user_id);

		if (user.isEmpty()) {
			throw new UserExceptions("User not exist with id: " + user_id);
		}

		userRepository.delete(user.get());
		return true;
	}

	@Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        System.out.println("Extracted email from JWT: " + email);

        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("No user found with email: " + email);
        } else {
            System.out.println("User found: " + user.getFirstName() + " " + user.getLastName());
        }

        return user;
    }


}

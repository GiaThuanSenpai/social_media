package com.social.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.exceptions.UserExceptions;
import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.service.UserServiceImpl;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userService;

	@GetMapping("/api/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userRepository.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/api/users/{user_id}")
	public ResponseEntity<User> getUserById(@PathVariable("user_id") Integer user_id) throws UserExceptions {
		try {
			User user = userService.findUserById(user_id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/api/users")
	public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) {
		User reqUser = userService.findUserByJwt(jwt);
		if (reqUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			boolean isUpdated = userService.updateUser(user, reqUser.getUser_id());
			if (isUpdated) {
				User updatedUser = userService.findUserByJwt(jwt); // Lấy thông tin người dùng sau khi cập nhật thành																	// công
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/api/users/follow/{user_id2}")
	public ResponseEntity<User> followUser(@RequestHeader("Authorization") String jwt,
			@PathVariable("user_id2") Integer user_id2) {
		User reqUser = userService.findUserByJwt(jwt);
		if (reqUser == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		try {
			User user = userService.followUser(reqUser.getUser_id(), user_id2);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/api/users/{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable("user_id") Integer user_id) {
		try {
			boolean isDeleted = userService.deleteUser(user_id);
			if (isDeleted) {
				return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User deletion failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/api/users/search")
	public List<User> searchUser(@RequestParam("query") String query) {
		List<User> users = userService.searchUser(query);
		return users;
	}

	@GetMapping("/api/users/profile")
	public ResponseEntity<User> getUserFromToken(@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserByJwt(jwt);
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		user.setPassword(null);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}

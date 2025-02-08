package com.social.controller;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.config.JwtProvider;
import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.request.LoginRequest;
import com.social.response.AuthResponse;
import com.social.service.CustomerUserDetailsService;
import com.social.service.IUserService;
import com.social.utils.EmailUtil;
import com.social.utils.OtpUtil;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IUserService userService;
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	@Autowired
	private OtpUtil otpUtil;
	@Autowired
	private EmailUtil emailUtil;

	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {
		User isExist = userRepository.findByEmail(user.getEmail());
		if (isExist != null) {
			throw new Exception("This email is already used with another account");
		}

		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(user.getEmail(), otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send OTP. Please try again");
		}

		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setUser_id(user.getUser_id());
		newUser.setGender(user.getGender());
		newUser.setOtp(otp);
		newUser.setOtpGeneratedTime(LocalDateTime.now());

		User savedUser = userRepository.save(newUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());

		String token = JwtProvider.generateToken(authentication);

		AuthResponse res = new AuthResponse(token, "Register Success");

		return res;
	}

	@PutMapping("/verify-account")
	public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp){
		User user = userRepository.findByEmail(email);
		if (user == null) {
			  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + email);		}
		if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (2 * 60)) {
	        user.setActive(true);
	        userRepository.save(user); // Lưu thay đổi vào cơ sở dữ liệu
	        return ResponseEntity.ok("OTP verified and account activated");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please regenerate otp and try again");
	    }
	}
	@PutMapping("/regenerate-otp")
	public String regenerateOtp(@RequestParam String email){
		User user = userRepository.findByEmail(email);
		if (user == null) {
			  throw new RuntimeException("User not found with email: " + email);	  
		}
		String otp = otpUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(email, otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send OTP. Pleas try again");
		}
		user.setOtp(otp);
		user.setOtpGeneratedTime(LocalDateTime.now());
		userRepository.save(user);
		return "Email send... please verify";
	}
	@PostMapping("/signin")
	public AuthResponse signin(@RequestBody LoginRequest loginRequest) {
		AuthResponse res;
		User user = userRepository.findByEmail(loginRequest.getEmail());
		if(!user.isActive()) {
			return res = new AuthResponse("", "Your account is not verified");
		}
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		String token = JwtProvider.generateToken(authentication);

		res = new AuthResponse(token, "Login Success");

		return res;
	}

	private Authentication authenticate(String email, String password) {
		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username");

		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Password not matched");

		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	}

}

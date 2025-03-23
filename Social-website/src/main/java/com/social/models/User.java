package com.social.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	private static final String DEFAULT_AVATAR_URL = "https://res.cloudinary.com/ddqygrb0g/image/upload/v1721653511/default-avatar_lxvn0y.jpg";
	private static final String DEFAULT_BACKGROUND_URL = "http://res.cloudinary.com/ddqygrb0g/image/upload/v1721653501/default-background_pmiblo.jpg";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer user_id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	
	private String imgAvatar;
	private String imgBackground;
	
	private boolean active;
	private String otp;
	private LocalDateTime otpGeneratedTime;
	
	
	@ElementCollection
	private List<Integer> followers = new ArrayList<>();
	@ElementCollection
	private List<Integer> followings = new ArrayList<>();

	@JsonIgnore
	@ManyToMany
	private List<Post> savedPost = new ArrayList<>();

	public User() {
		this.imgAvatar = DEFAULT_AVATAR_URL;
        this.imgBackground = DEFAULT_BACKGROUND_URL;
	}

	

	public String getImgAvatar() {
		return imgAvatar;
	}



	public void setImgAvatar(String imgAvatar) {
		this.imgAvatar = imgAvatar;
	}



	public String getImgBackground() {
		return imgBackground;
	}



	public void setImgBackground(String imgBackground) {
		this.imgBackground = imgBackground;
	}



	public User(Integer user_id, String firstName, String lastName, String email, String password, String gender,
			boolean active, String otp, LocalDateTime otpGeneratedTime, List<Integer> followers,
			List<Integer> followings, List<Post> savedPost) {
		super();
		this.user_id = user_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.active = active;
		this.otp = otp;
		this.otpGeneratedTime = otpGeneratedTime;
		this.followers = followers;
		this.followings = followings;
		this.savedPost = savedPost;
	}
	
	



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public String getOtp() {
		return otp;
	}



	public void setOtp(String otp) {
		this.otp = otp;
	}



	public LocalDateTime getOtpGeneratedTime() {
		return otpGeneratedTime;
	}



	public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
		this.otpGeneratedTime = otpGeneratedTime;
	}



	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Integer> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Integer> followers) {
		this.followers = followers;
	}

	public List<Integer> getFollowings() {
		return followings;
	}

	public void setFollowings(List<Integer> followings) {
		this.followings = followings;
	}

	public List<Post> getSavedPost() {
		return savedPost;
	}

	public void setSavedPost(List<Post> savedPost) {
		this.savedPost = savedPost;
	}
}

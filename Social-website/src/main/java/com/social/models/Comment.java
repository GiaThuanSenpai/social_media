package com.social.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cmt_id;
	
	private String content;
	
	@ManyToOne
	private User user;
	
	@ManyToMany
	private List<User> liked = new ArrayList<>();
	
	private LocalDateTime createdAt;
	
	
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Integer getCmt_id() {
		return cmt_id;
	}

	public void setCmt_id(Integer cmt_id) {
		this.cmt_id = cmt_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getLiked() {
		return liked;
	}

	public void setLiked(List<User> liked) {
		this.liked = liked;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Comment(Integer cmt_id, String content, User user, List<User> liked, LocalDateTime createdAt) {
		super();
		this.cmt_id = cmt_id;
		this.content = content;
		this.user = user;
		this.liked = liked;
		this.createdAt = createdAt;
	}
	
	
	
}

package com.social.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.social.models.Reels;

public interface ReelsRepository extends JpaRepository<Reels, Integer> {

	@Query("SELECT r FROM Reels r WHERE r.user.user_id = :user_id")
	List<Reels> findReelsByUserId(@Param("user_id") Integer user_id);
}

package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.social.models.Story;

public interface StoryRepository extends JpaRepository<Story, Integer> {

	@Query("select s from Story s where s.user.id=:user_id")
	List<Story> findStoryByUserId(@Param("user_id") Integer user_id);
}

package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.social.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.chat.id=:chat_id")
	List<Message> findByChatId(@Param("chat_id") Integer chat_id);
}

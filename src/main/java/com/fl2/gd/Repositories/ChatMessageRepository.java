package com.fl2.gd.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fl2.gd.Entities.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
	@Query("SELECT cm FROM ChatMessage cm " +
		       "WHERE cm.departement.departementId = :departementId")
    List<ChatMessage> findByDepartementId(int departementId);
}

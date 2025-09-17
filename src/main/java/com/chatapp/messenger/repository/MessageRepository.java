package com.chatapp.messenger.repository;

import com.chatapp.messenger.domain.Message;
import com.chatapp.messenger.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}

package com.chatapp.messenger.repository;

import com.chatapp.messenger.domain.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    void deleteByUserIdAndChatRoomId(Long userId, Long ChatRoomId);
}

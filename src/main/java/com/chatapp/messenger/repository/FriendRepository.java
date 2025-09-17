package com.chatapp.messenger.repository;

import com.chatapp.messenger.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}

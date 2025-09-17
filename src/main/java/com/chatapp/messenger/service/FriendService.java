package com.chatapp.messenger.service;

import com.chatapp.messenger.domain.Friend;
import com.chatapp.messenger.domain.User;
import com.chatapp.messenger.exception.NonExistentFriendException;
import com.chatapp.messenger.exception.NonExistentUserException;
import com.chatapp.messenger.repository.FriendRepository;
import com.chatapp.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    /**
     * 친구 요청
     * @param senderUserId
     * @param receiverUserId
     */
    public Long friendRequest(Long senderUserId, Long receiverUserId){
        User sender = userRepository.findById(senderUserId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 유저입니다."));
        User receiver = userRepository.findById(receiverUserId)
                .orElseThrow(() -> new NonExistentUserException("존재하지 않는 유저입니다."));

        Friend friend = Friend.sendFriendRequest(sender, receiver);

        friendRepository.save(friend);

        return friend.getId();
    }

    /**
     * 친구 요청 수락
     * @param friendId
     */
    public void acceptFriendRequest(Long friendId) {
        Friend request = friendRepository.findById(friendId)
                .orElseThrow(() -> new NonExistentFriendException("존재하지 않는 친구요청입니다."));

        request.accept();
    }

    /**
     * 친구 차단
     * @param friendId
     */
    public void blockFriend(Long friendId) {
        Friend request = friendRepository.findById(friendId)
                .orElseThrow(() -> new NonExistentFriendException("존재하지 않는 친구입니다."));

        request.block();
    }

    /**
     * 친구 차단 해제
     * @param friendId
     */
    public void unBlockFriend(Long friendId) {
        Friend request = friendRepository.findById(friendId)
                .orElseThrow(() -> new NonExistentFriendException("존재하지 않는 친구입니다."));

        request.unBlock();
    }
}

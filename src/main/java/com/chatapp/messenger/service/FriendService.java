package com.chatapp.messenger.service;

import com.chatapp.messenger.domain.Friend;
import com.chatapp.messenger.domain.User;
import com.chatapp.messenger.exception.BusinessException;
import com.chatapp.messenger.exception.errorcode.ErrorCode;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        User receiver = userRepository.findById(receiverUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

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
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST));

        request.accept();
    }

    /**
     * 친구 차단
     * @param friendId
     */
    public void blockFriend(Long friendId) {
        Friend request = friendRepository.findById(friendId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FRIEND_NOT_FOUND));

        request.block();
    }

    /**
     * 친구 차단 해제
     * @param friendId
     */
    public void unBlockFriend(Long friendId) {
        Friend request = friendRepository.findById(friendId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FRIEND_NOT_FOUND));

        request.unBlock();
    }
}

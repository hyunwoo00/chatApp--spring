package com.chatapp.messenger.service;


import com.chatapp.messenger.domain.*;
import com.chatapp.messenger.exception.BusinessException;
import com.chatapp.messenger.exception.errorcode.ErrorCode;
import com.chatapp.messenger.repository.ChatRoomRepository;
import com.chatapp.messenger.repository.ChatRoomUserRepository;
import com.chatapp.messenger.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    /**
     * 개인채팅방 생성
     * @param userIds 참여자Id
     */
    public Long createPrivateChatRoom(List<Long> userIds) {
        PrivateChatRoom privateChatRoom = new PrivateChatRoom();
        userIds.stream().forEach(userId ->
                privateChatRoom.addParticipant(
                        new ChatRoomUser(privateChatRoom,
                                userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND))
                        )
                )
        );

        chatRoomRepository.save(privateChatRoom);

        return privateChatRoom.getId();

    }

    /**
     * 그룹채팅방 생성
     * @param userIds 참여자Id
     * @param roomManagerId 방장Id
     */
    public Long createGroupChatRoom(List<Long> userIds, Long roomManagerId) {
        GroupChatRoom groupChatRoom = new GroupChatRoom();
        userIds.stream()
                .map(userId -> {
                    ChatRoomUser chatRoomUser = new ChatRoomUser(
                            groupChatRoom,
                            userRepository.findById(userId).orElseThrow()
                    );

                    if (userId.equals(roomManagerId)) {
                        chatRoomUser.setRoomManager();
                    }

                    return chatRoomUser;
                })
                .forEach(groupChatRoom::addParticipant);

        chatRoomRepository.save(groupChatRoom);

        return groupChatRoom.getId();
    }

    /**
     * 채팅창 나가기
     */
    public void leaveChatRoom(Long userId, Long chatRoomId) {
        chatRoomUserRepository.deleteByUserIdAndChatRoomId(userId, chatRoomId);
    }

    /**
     * 친구 초대
     * 개인채팅방일 경우 -> 새 그룹채팅방 생성 & 친구 초대
     * 그룹채팅방일 경우 -> 친구초대
     */
    public void inviteToChatRoom(Long invitingUserId, List<Long> invitedUserIds, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHATROOM_NOT_FOUND));

        User invitingUser = userRepository.findById(invitingUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        boolean isExist = chatRoom.getParticipants().stream()
                .map(ChatRoomUser::getUser)
                .anyMatch(user -> user.equals(invitingUser));

        //채팅방에 존재하지 않는 유저가 초대할 경우 예외 처리.
        if(!isExist){
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }


        if(chatRoom instanceof PrivateChatRoom) {
            //기존 채팅방 유저들의 id값 추출.
            List<Long> userIds = new java.util.ArrayList<>(
                    chatRoom.getParticipants()
                    .stream()
                    .map(chatRoomUser -> chatRoomUser.getUser().getId())
                    .toList()
            );

            userIds.addAll(invitedUserIds);

            createGroupChatRoom(userIds, invitingUserId);
        }
        else if(chatRoom instanceof GroupChatRoom) {
            invitedUserIds.stream().forEach(invitedUserId ->
                chatRoom.addParticipant(
                        new ChatRoomUser(chatRoom, userRepository.findById(invitedUserId)
                                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND))
                        )
                )
            );
        }
    }

    /**
     * 그룹 채팅방 이름 변경(방장만 변경 가능)
     */
    public void changeChatRoomName(Long chatRoomId, String name) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHATROOM_NOT_FOUND));

        if (chatRoom instanceof GroupChatRoom groupChatRoom) {
            groupChatRoom.setName(name);
        }
    }


}

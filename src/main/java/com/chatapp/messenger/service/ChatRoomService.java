package com.chatapp.messenger.service;


import com.chatapp.messenger.domain.ChatRoomUser;
import com.chatapp.messenger.domain.GroupChatRoom;
import com.chatapp.messenger.domain.PrivateChatRoom;
import com.chatapp.messenger.repository.ChatRoomRepository;
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

    /**
     * 개인채팅방 생성
     * @param userIds 참여자Id
     */
    public Long createPrivateChatRoom(List<Long> userIds) {
        PrivateChatRoom privateChatRoom = new PrivateChatRoom();
        userIds.stream().forEach(userId ->
                privateChatRoom.addParticipant(
                        new ChatRoomUser(privateChatRoom,
                                userRepository.findById(userId).orElseThrow()
                        )
                )
        );

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

        return groupChatRoom.getId();
    }



}

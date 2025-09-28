package com.chatapp.messenger.controller;

import com.chatapp.messenger.dto.ChatRoomInviteRequest;
import com.chatapp.messenger.dto.ChatRoomRequest;
import com.chatapp.messenger.dto.ChatRoomResponse;
import com.chatapp.messenger.exception.BusinessException;
import com.chatapp.messenger.exception.errorcode.ErrorCode;
import com.chatapp.messenger.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    /**
     * 채팅방 생성
     */
    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        List<Long> participantsIds = chatRoomRequest.getParticipantsIds();

        Long chatRoomId;
        if (participantsIds.size() == 2) {
            chatRoomId = chatRoomService.createPrivateChatRoom(participantsIds);
        }
        else if (participantsIds.size() > 2) {
            chatRoomId = chatRoomService.createGroupChatRoom(participantsIds, chatRoomRequest.getRoomMangerId());
        }
        else {
            throw(new BusinessException(ErrorCode.INVALID_PARTICIPANTS));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ChatRoomResponse(chatRoomId));
    }

    /**
     * 친구 초대
     */
    @PostMapping("/{chatRoomId}/participants")
    public ResponseEntity<Void> inviteParticipants(@PathVariable Long chatRoomId, @RequestBody ChatRoomInviteRequest request) {
        chatRoomService.inviteToChatRoom(request.getInvitingUserId(), request.getInvitedUserId(), chatRoomId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 채팅방 나가기
     */
    @DeleteMapping("/{chatRoomId}/{userId}")
    public ResponseEntity<Void> leaveChatRoom(@PathVariable Long chatRoomId,@PathVariable Long userId) {
        chatRoomService.leaveChatRoom(userId, chatRoomId);

        return ResponseEntity.noContent().build();
    }

}

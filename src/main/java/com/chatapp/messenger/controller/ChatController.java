package com.chatapp.messenger.controller;

import com.chatapp.messenger.controller.util.KakaoUserInfo;
import com.chatapp.messenger.domain.ChatRoom;
import com.chatapp.messenger.domain.Message;
import com.chatapp.messenger.domain.User;
import com.chatapp.messenger.dto.MessageRequestDto;
import com.chatapp.messenger.dto.MessageResponseDto;
import com.chatapp.messenger.exception.BusinessException;
import com.chatapp.messenger.exception.errorcode.ErrorCode;
import com.chatapp.messenger.repository.ChatRoomRepository;
import com.chatapp.messenger.repository.MessageRepository;
import com.chatapp.messenger.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ChatController {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    //클라이언트가 "/app/chat/{roomId}"로 보낸 메세지 처리.
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public MessageResponseDto send(@DestinationVariable String roomId, MessageRequestDto request, @AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);
        String email = kakaoUserInfo.getEmail();

        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(roomId))
                .orElseThrow(() -> new BusinessException(ErrorCode.CHATROOM_NOT_FOUND));


        Message message = new Message(chatRoom, sender, request.getContent(), request.getType(), LocalDateTime.now());
        messageRepository.save(message);

        return new MessageResponseDto(
                sender.getNickName(),
                message.getContent(),
                message.getType(),
                message.getSentAt()
        );
    }
}

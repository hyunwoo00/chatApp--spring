package com.chatapp.messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MessageResponseDto {
    private String senderName;
    private String content;
    private String type;
    private LocalDateTime sentAt;
}
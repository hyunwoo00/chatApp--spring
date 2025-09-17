package com.chatapp.messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageRequestDto {
    private String senderName;
    private String content;
    private String type;
}

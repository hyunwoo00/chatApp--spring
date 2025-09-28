package com.chatapp.messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatRoomRequest {
    private List<Long> participantsIds;
    private Long roomMangerId;
}

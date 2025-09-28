package com.chatapp.messenger.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ChatRoomInviteRequest {
    private Long invitingUserId; //초대하는 사람의 id
    private List<Long> invitedUserId;
}

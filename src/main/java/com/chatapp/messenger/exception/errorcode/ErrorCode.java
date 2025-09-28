package com.chatapp.messenger.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_REQUEST(400, "잘못된 요청입니다."),
    USER_NOT_FOUND(404, "유저를 찾을 수 없습니다."),
    CHATROOM_NOT_FOUND(404, "채팅방을 찾을 수 없습니다."),
    FRIEND_NOT_FOUND(404, "친구를 찾을 수 없습니다."),
    INVALID_PARTICIPANTS(400, "참가자는 최소 2명 이상이어야 합니다."),
    INTERNAL_ERROR(500, "서버 내부 오류입니다.");

    private final int status;
    private final String message;
}

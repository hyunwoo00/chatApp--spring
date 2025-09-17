package com.chatapp.messenger.exception;

public class NonExistentChatRoomException extends RuntimeException {
    public NonExistentChatRoomException() {
    }

    public NonExistentChatRoomException(String message) {
        super(message);
    }

    public NonExistentChatRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}

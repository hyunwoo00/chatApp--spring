package com.chatapp.messenger.exception;

public class NonExistentFriendException extends RuntimeException{

    public NonExistentFriendException() {
    }

    public NonExistentFriendException(String message) {
        super(message);
    }
}

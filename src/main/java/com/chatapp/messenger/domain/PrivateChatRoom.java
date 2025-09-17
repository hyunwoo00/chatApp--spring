package com.chatapp.messenger.domain;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PRIVATE")
@NoArgsConstructor
public class PrivateChatRoom extends ChatRoom{
}

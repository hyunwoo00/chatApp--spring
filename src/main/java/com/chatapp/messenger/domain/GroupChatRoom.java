package com.chatapp.messenger.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("GROUP")
@NoArgsConstructor
public class GroupChatRoom extends ChatRoom{
    private final int maxUsers = 100; //최대 인원수
    @Setter
    private String name; //채팅방 이름, null(defualt): 참여자 이름 나열. or 방장이 설정 가능.

    public GroupChatRoom(String name) {
        this.name = name;
    }

}

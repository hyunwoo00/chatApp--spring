package com.chatapp.messenger.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(nullable = false, length = 1000)
    private String content; // 메시지 본문

    @Column(nullable = false)
    private String type; // TEXT, IMAGE, FILE 등

    @Column(nullable = false)
    private LocalDateTime sentAt;

    public Message(ChatRoom chatRoom, User sender, String content, String type, LocalDateTime sentAt) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.content = content;
        this.type = type;
        this.sentAt = sentAt;
    }
}

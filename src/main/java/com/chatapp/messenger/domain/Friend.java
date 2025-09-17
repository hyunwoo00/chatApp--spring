package com.chatapp.messenger.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 요청한 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_user_id")
    private User friend; // 친구 대상 유저

    @Column(nullable = false)
    private boolean accepted; // true = 친구 수락됨

    @Column(nullable = false)
    private boolean blocked; // true = 친구 수락됨

    public static Friend sendFriendRequest(User sender, User receiver) {
        Friend friend = new Friend();
        friend.setUser(sender);
        friend.setFriend(receiver);
        friend.setAccepted(false);
        friend.setBlocked(false);

        return friend;
    }

    public void accept() {
        this.accepted = true;
    }

    public void block() { this.blocked = true; }

    public void unBlock() { this.blocked = false; }
}

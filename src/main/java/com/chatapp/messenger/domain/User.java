package com.chatapp.messenger.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String oauthId;

    @Column(nullable = false)
    private String oauthType;

    public User(String nickName, String email, String oauthId, String oauthType) {
        this.nickName = nickName;
        this.email = email;
        this.oauthId = oauthId;
        this.oauthType = oauthType;
    }

}

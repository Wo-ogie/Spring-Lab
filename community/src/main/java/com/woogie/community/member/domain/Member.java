package com.woogie.community.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Nickname nickname;

    //== Constructor ==//
    @Builder
    private Member(String email, String nickname) {
        this.nickname = new Nickname(nickname);
        this.email = new Email(email);
    }

    //== Business Logic ==//
    public void update(String email, String nickname) {
        this.setEmail(email);
        this.setNickname(nickname);
    }

    //== Getter, Setter ==//
    public String getEmail() {
        return email.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }

    // Embedded value type은 immutable object로 관리하여 side effect를 최소화한다.
    private void setEmail(String email) {
        this.email = new Email(email);
    }

    private void setNickname(String nickname) {
        this.nickname = new Nickname(nickname);
    }
}
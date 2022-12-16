package com.woogie.community.member.domain;

import com.woogie.community.member.exception.NicknameNullOrEmptyException;
import com.woogie.community.member.exception.NicknameTooLongException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Nickname {

    private static final int MAX_NICKNAME_LENGTH = 255;

    @Column(name = "nickname", nullable = false, unique = true)
    private String value;

    public Nickname(String value) {
        validateNullOrEmpty(value);
        validateLength(value);
        this.value = value;
    }

    private void validateNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.trim().length() < 1) {
            throw new NicknameNullOrEmptyException();
        }
    }

    private void validateLength(String value) {
        if (value.length() > MAX_NICKNAME_LENGTH) {
            throw new NicknameTooLongException();
        }
    }
}

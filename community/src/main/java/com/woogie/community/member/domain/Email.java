package com.woogie.community.member.domain;

import com.woogie.community.member.exception.EmailFormatException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,4}$");

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    public Email(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String value) {
        Matcher matcher = EMAIL_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new EmailFormatException();
        }
    }
}

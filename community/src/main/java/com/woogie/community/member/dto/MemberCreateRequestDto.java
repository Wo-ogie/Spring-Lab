package com.woogie.community.member.dto;

import com.woogie.community.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberCreateRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}

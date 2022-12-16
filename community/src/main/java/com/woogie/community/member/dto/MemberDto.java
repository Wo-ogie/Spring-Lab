package com.woogie.community.member.dto;

import com.woogie.community.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberDto {

    private String email;
    private String nickname;

    public MemberDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}

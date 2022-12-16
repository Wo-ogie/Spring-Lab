package com.woogie.community.member.dto;

import com.woogie.community.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberCreateResponseDto {

    private Long id;

    public MemberCreateResponseDto(Member member) {
        this.id = member.getId();
    }
}

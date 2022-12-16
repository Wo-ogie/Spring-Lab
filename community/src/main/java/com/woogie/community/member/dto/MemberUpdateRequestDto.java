package com.woogie.community.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class MemberUpdateRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;
}

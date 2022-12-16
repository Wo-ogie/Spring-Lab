package com.woogie.community.member.exception;

import com.woogie.community.common.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException(long memberId) {
        super(String.format("(memberId = %d)", memberId));
    }
}

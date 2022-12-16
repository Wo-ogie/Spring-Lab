package com.woogie.community.member.service;

import com.woogie.community.member.dto.MemberCreateRequestDto;
import com.woogie.community.member.dto.MemberCreateResponseDto;
import com.woogie.community.member.dto.MemberDto;
import com.woogie.community.member.dto.MemberUpdateRequestDto;
import com.woogie.community.member.exception.EmailDuplicateException;
import com.woogie.community.member.exception.MemberNotFoundException;
import com.woogie.community.member.exception.NicknameDuplicateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 저장")
    void save() {
        // when
        MemberCreateResponseDto response = createTestMember();

        // then
        assertThat(response.getId()).isNotNull();
    }

    @Test
    @DisplayName("회원 이메일 중복 저장 예외")
    void saveEmailDuplicateException() {
        // given
        createTestMember();

        // when, then
        assertThatThrownBy(this::createTestMember)
                .isInstanceOf(EmailDuplicateException.class);
    }

    @Test
    @DisplayName("회원 단건 조회")
    void find() {
        // given
        MemberCreateResponseDto memberCreateResponse = createTestMember();

        // when
        MemberDto findMemberDto = memberService.findById(memberCreateResponse.getId());

        // then
        assertAll(
                () -> assertThat(findMemberDto.getEmail()).isEqualTo("test@test.com"),
                () -> assertThat(findMemberDto.getNickname()).isEqualTo("test")
        );
    }

    @Test
    @DisplayName("회원 수정")
    void update() {
        // given
        MemberCreateResponseDto response = createTestMember();

        // when
        MemberUpdateRequestDto request = new MemberUpdateRequestDto("update@test.com", "update");
        memberService.update(response.getId(), request);

        // then
        MemberDto findMemberDto = memberService.findById(response.getId());
        assertAll(
                () -> assertThat(findMemberDto.getEmail()).isEqualTo("update@test.com"),
                () -> assertThat(findMemberDto.getNickname()).isEqualTo("update")
        );
    }
    
    @Test
    @DisplayName("회원 이름 중복 수정 예외")
    void updateNicknameDuplicateException() {
        // given
        MemberCreateResponseDto response = createTestMember();

        // when
        MemberUpdateRequestDto request = new MemberUpdateRequestDto("update@test.com", "test");

        // then
        assertThatThrownBy(() -> memberService.update(response.getId(), request))
                .isInstanceOf(NicknameDuplicateException.class);
    }

    @Test
    @DisplayName("회원 삭제")
    void delete() {
        // given
        MemberCreateResponseDto response = createTestMember();

        // when
        memberService.delete(response.getId());

        // then
        assertThatThrownBy(() -> memberService.findById(response.getId()))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 회원 삭제 예외")
    void deleteNotExistsException() {
        // when, then
        assertThatThrownBy(() -> memberService.delete(1L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    private MemberCreateRequestDto createTestMemberRequest() {
        return new MemberCreateRequestDto("test@test.com", "test");
    }

    private MemberCreateResponseDto createTestMember() {
        MemberCreateRequestDto request = createTestMemberRequest();
        return memberService.join(request);
    }
}
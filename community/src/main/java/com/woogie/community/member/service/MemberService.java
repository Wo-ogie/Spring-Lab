package com.woogie.community.member.service;

import com.woogie.community.member.domain.Email;
import com.woogie.community.member.domain.Member;
import com.woogie.community.member.domain.Nickname;
import com.woogie.community.member.dto.MemberCreateRequestDto;
import com.woogie.community.member.dto.MemberCreateResponseDto;
import com.woogie.community.member.dto.MemberDto;
import com.woogie.community.member.dto.MemberUpdateRequestDto;
import com.woogie.community.member.exception.EmailDuplicateException;
import com.woogie.community.member.exception.MemberNotFoundException;
import com.woogie.community.member.exception.NicknameDuplicateException;
import com.woogie.community.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberCreateResponseDto join(MemberCreateRequestDto request) {
        validateDuplicate(request.getEmail(), request.getNickname());
        return new MemberCreateResponseDto(memberRepository.save(request.toEntity()));
    }

    public MemberDto findById(Long memberId) {
        Member member = getMemberById(memberId);
        return new MemberDto(member);
    }

    @Transactional
    public void update(Long memberId, MemberUpdateRequestDto request) {
        validateDuplicate(request.getEmail(), request.getNickname());
        Member member = getMemberById(memberId);
        member.update(request.getEmail(), request.getNickname());
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = getMemberById(memberId);
        memberRepository.delete(member);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private void validateDuplicate(String email, String nickname) {
        if (memberRepository.existsByEmail(new Email(email))) {
            throw new EmailDuplicateException();
        }
        if (memberRepository.existsByNickname(new Nickname(nickname))) {
            throw new NicknameDuplicateException();
        }
    }
}

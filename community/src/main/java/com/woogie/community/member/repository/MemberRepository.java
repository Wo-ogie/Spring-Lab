package com.woogie.community.member.repository;

import com.woogie.community.member.domain.Email;
import com.woogie.community.member.domain.Member;
import com.woogie.community.member.domain.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // TODO: Parameter를 String email로 받을 수 있도록 변경
    boolean existsByEmail(Email email);

    // TODO: Parameter를 String nickname으로 받을 수 있도록 변경
    boolean existsByNickname(Nickname nickname);
}

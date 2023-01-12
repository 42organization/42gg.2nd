package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Long join(String username, String password) {
        Member member = Member.builder().
                username(username).passwd(password).build();
        memberRepository.save(member);
        return member.getId();
    }
}

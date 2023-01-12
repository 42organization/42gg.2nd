package com.example.demo.controller;

import com.example.demo.dto.member.MemberJoinRequestDto;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> joinMember(@RequestBody MemberJoinRequestDto data) {
        Long id = memberService.join(data.getUsername(), data.getPassword());
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

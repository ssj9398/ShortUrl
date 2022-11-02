package com.example.shorturl.controller;

import com.example.shorturl.common.advice.Success;
import com.example.shorturl.dto.request.MemberRequestDto;
import com.example.shorturl.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("member/join")
    public ResponseEntity<Success> addMember(@RequestBody MemberRequestDto.Create create){
        memberService.addMember(create);
        return new ResponseEntity<>(new Success("회원가입 완료",""), HttpStatus.CREATED);
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("member/login")
    public ResponseEntity<Success> loginMember(@RequestBody MemberRequestDto.Login login){
        memberService.loginMember(login);
        return new ResponseEntity<>(new Success("로그인 성공",""),HttpStatus.OK);
    }
}

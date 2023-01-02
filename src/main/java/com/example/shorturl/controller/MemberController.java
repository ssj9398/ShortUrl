package com.example.shorturl.controller;

import com.example.shorturl.common.advice.ResultInfo;
import com.example.shorturl.common.advice.Success;
import com.example.shorturl.dto.request.MemberRequestDto;
import com.example.shorturl.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("member/join")
    public ResultInfo addMember(@RequestBody MemberRequestDto.Create create){
        return new ResultInfo(ResultInfo.Code.CREATE,"회원가입 완료",memberService.addMember(create));
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("member/login")
    public ResultInfo loginMember(@RequestBody MemberRequestDto.Login login){
        return new ResultInfo(ResultInfo.Code.SUCCESS,"로그인 성공",memberService.loginMember(login));
    }
}

package com.gondoc.rebrary.controller.api;

import com.gondoc.rebrary.component.MailSender;
import com.gondoc.rebrary.component.util.PasswordUtil;
import com.gondoc.rebrary.component.util.RedisUtils;
import com.gondoc.rebrary.config.security.JwtTokenProvider;
import com.gondoc.rebrary.domain.common.dto.Response;
import com.gondoc.rebrary.domain.member.dto.MemberDto;
import com.gondoc.rebrary.domain.member.dto.VerifyDto;
import com.gondoc.rebrary.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(value = "/api/user")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signUp")
    public Response<?> signUp(@RequestBody MemberDto memberDto) {
        try {
            return Response.ok(memberService.signUp(memberDto));
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/checkId/{id}")
    public Response<?> idCheck(@PathVariable String id) {
        try {
            return Response.ok(memberService.idCheck(id));
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/checkNick/{nick}")
    public Response<?> nickCheck(@PathVariable String nick) {
        try {
            return Response.ok(memberService.nickCheck(nick));
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Response<?> login(@RequestBody MemberDto memberDto) {
        try {
//            MemberDto dto = memberService.getMember(user.get("userId"));
            MemberDto dto = memberService.getMember(memberDto.getEmail());
            String token = jwtTokenProvider.createToken(dto.getEmail());
            if (!PasswordUtil.matches(memberDto.getPassword(), dto.getPassword())) {
                throw new NoResultException("비밀번호가 맞지 않습니다.");
            }
            return Response.justOk();
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/reqCd")
    public Response<Long> sendVerifyCodeToEmail(@RequestBody Map<String, String> reqMap) {
        try {
            return Response.ok(memberService.sendVerifyCode(reqMap.get("email")));
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/verifyCd")
    public Response<Boolean> validVerifyCode(@RequestBody VerifyDto verifyDto) {
        try {
            return Response.ok(memberService.validVerifyCode(verifyDto));
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/nick")
    public Response<?> sendRandomNick() {
        try {
            return Response.ok(memberService.sendRandomNick());
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

//    @GetMapping("/send")
//    public Response<?> send(@RequestParam String email) {
//        try {
//            return Response.ok(sender.send(email));
//        } catch (Exception e) {
//            return Response.fail(e.getMessage());
//        }
//    }
}

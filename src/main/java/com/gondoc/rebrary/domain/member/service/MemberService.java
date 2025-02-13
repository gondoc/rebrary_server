package com.gondoc.rebrary.domain.member.service;

import com.gondoc.rebrary.component.util.RandomUtil;
import com.gondoc.rebrary.component.util.RedisUtils;
import com.gondoc.rebrary.config.constant.KorAdjective;
import com.gondoc.rebrary.domain.member.dto.MemberDto;
import com.gondoc.rebrary.domain.member.dto.VerifyDto;
import com.gondoc.rebrary.domain.member.entity.MemberEntity;
import com.gondoc.rebrary.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("UserService")
public class MemberService {

    private final MemberRepository memberRepository;
    private final KorAdjective korAdjective;
    private final RandomUtil randomUtil;
    private final RedisUtils redisUtils;

    public boolean signUp(MemberDto dto) throws Exception {
        try {
            MemberEntity save = memberRepository.save(dto.toMemberEntity());
            log.info("User saved: {}", save);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean idCheck(String email) throws Exception {
        try {
            Optional<MemberEntity> byUserEmail = memberRepository.findByEmail(email);
            return byUserEmail.isPresent();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean nickCheck(String nick) throws Exception {
        try {
            Optional<MemberEntity> byUserNick = memberRepository.findByNickName(nick);
            return byUserNick.isPresent();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public MemberDto getMember(String email) throws Exception {
        Optional<MemberEntity> opEntity = memberRepository.findByEmail(email);
        if (!opEntity.isPresent()) {
            throw new Exception(String.format("User %s not found", email));
        } else {
            return opEntity.get().toMemberDto();
        }
    }

    public boolean validVerifyCode(VerifyDto verifyDto) {
        try {
            Optional<String> opVerifyCode = redisUtils.get(verifyDto.getEmail(), String.class);
            if (opVerifyCode.isPresent()) {
                boolean isEqual = opVerifyCode.get().equals(verifyDto.getCode());
                return isEqual;
            } else {
                return false;
            }
//            return opVerifyCode.isPresent() && opVerifyCode.get().equals(verifyDto.getCode());

//            int randomIntByMax = randomUtil.getRandomIntByMax(10);
//            log.info("randomIntByMax is {} ", randomIntByMax);
//            boolean result = randomIntByMax > 5;
//            log.info("result is {} ", result);
//            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String sendRandomNick() {
        String adjective = korAdjective.findIndex(randomUtil.getRandomIntByMax(korAdjective.getLength()));
        String randomInt = String.valueOf(randomUtil.getRandomIntValuesByLength(4));
        return adjective.concat("리브인").concat(randomInt);
    }
}

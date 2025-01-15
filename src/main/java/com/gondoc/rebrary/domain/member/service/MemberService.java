package com.gondoc.rebrary.domain.member.service;

import com.gondoc.rebrary.domain.member.dto.MemberDto;
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

    public boolean signUp(MemberDto dto) throws Exception {
        try {
            MemberEntity save = memberRepository.save(dto.toMemberEntity());
            log.info("User saved: {}", save);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean idCheck(String id) throws Exception {
        try {
            Optional<MemberEntity> byUserId = memberRepository.findByUserId(id);
            if (byUserId.isPresent()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public MemberDto getMember(String memberId) throws Exception {
        Optional<MemberEntity> opEntity = memberRepository.findByUserId(memberId);
        if (!opEntity.isPresent()) {
            throw new Exception(String.format("User %s not found", memberId));
        } else {
            return opEntity.get().toMemberDto();
        }
    }

}

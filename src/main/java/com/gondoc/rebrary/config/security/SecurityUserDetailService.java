package com.gondoc.rebrary.config.security;

import com.gondoc.rebrary.domain.member.entity.MemberEntity;
import com.gondoc.rebrary.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> opDto = memberRepository.findByUserId(username);
        if (!opDto.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            MemberEntity memberEntity = opDto.get();
            return new SecurityUser(memberEntity.toMemberDto());
        }
    }
}

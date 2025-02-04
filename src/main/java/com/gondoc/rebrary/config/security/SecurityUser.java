package com.gondoc.rebrary.config.security;

import com.gondoc.rebrary.domain.member.dto.MemberDto;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {

    private MemberDto memberDto;

    public SecurityUser(MemberDto memberDto) {
        super(memberDto.getId(), memberDto.getPassword(), AuthorityUtils.createAuthorityList(memberDto.getEmail()));
        this.memberDto = memberDto;
    }

    public MemberDto getMemberDto() {
        return memberDto;
    }
}

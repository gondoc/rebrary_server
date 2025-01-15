package com.gondoc.rebrary.domain.member.dto;

import com.gondoc.rebrary.component.util.DateTimeUtil;
import com.gondoc.rebrary.component.util.PasswordUtil;
import com.gondoc.rebrary.domain.member.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String id;
    private String userId;
    private String password;
    private String email;
    private String nickName;
    private String createAt;
    private String updateAt;

    public MemberEntity toMemberEntity() {
        return MemberEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .password(PasswordUtil.encrypt(password)) // 여기
                .email(email)
                .nickName(nickName)
                .createAt(LocalDateTime.now().format(DateTimeUtil.getDefaultFormatter()))
                .build();
    }
}

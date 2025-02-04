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
    private String email;
    private String password;
    private String nickName;
    private String createAt;
    private String updateAt;
    private String recoveryEmail;

    public MemberEntity toMemberEntity() {
        return MemberEntity.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .password(PasswordUtil.encrypt(password))
                .nickName(nickName)
                .createAt(LocalDateTime.now().format(DateTimeUtil.getDefaultFormatter()))
                .recoveryEmail(recoveryEmail)
                .build();
    }
}

package com.gondoc.rebrary.domain.member.entity;

import com.gondoc.rebrary.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_member_info", schema = "member")
public class MemberEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_pw")
    private String password;

    @Column(name = "user_nick")
    private String nickName;

    @Column(name = "user_create_dtm")
    private String createAt;

    @Column(name = "user_update_dtm")
    private String updateAt;

    @Column(name = "user_recovery_email")
    private String recoveryEmail;

    public MemberDto toMemberDto() {
        return MemberDto.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickName(nickName)
                .createAt(createAt)
                .updateAt(updateAt)
                .updateAt(recoveryEmail)
                .build();
    }
}

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

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_at")
    private String updateAt;

    public MemberDto toMemberDto() {
        return MemberDto.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .email(email)
                .nickName(nickName)
                .createAt(createAt)
                .updateAt(updateAt)
                .build();
    }
}

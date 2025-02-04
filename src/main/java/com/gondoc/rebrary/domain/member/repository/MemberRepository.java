package com.gondoc.rebrary.domain.member.repository;

import com.gondoc.rebrary.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserRepository")
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    Optional<MemberEntity> findByEmail(String email);
}

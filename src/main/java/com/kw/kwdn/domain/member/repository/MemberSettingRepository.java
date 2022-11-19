package com.kw.kwdn.domain.member.repository;

import com.kw.kwdn.domain.member.MemberSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSettingRepository extends JpaRepository<MemberSetting, String> {
    Optional<MemberSetting> findOneById(String userId);
}

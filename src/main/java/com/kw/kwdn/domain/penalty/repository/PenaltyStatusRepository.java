package com.kw.kwdn.domain.penalty.repository;

import com.kw.kwdn.domain.penalty.PenaltyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PenaltyStatusRepository extends JpaRepository<PenaltyStatus, Long> {
    @Query("select p from PenaltyStatus as p where p.member.id=:memberId")
    Optional<PenaltyStatus> findByUserId(@Param(value = "memberId") String memberId);
}

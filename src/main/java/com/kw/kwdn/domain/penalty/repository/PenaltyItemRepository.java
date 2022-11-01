package com.kw.kwdn.domain.penalty.repository;

import com.kw.kwdn.domain.penalty.PenaltyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyItemRepository extends JpaRepository<PenaltyItem, Long> {
}

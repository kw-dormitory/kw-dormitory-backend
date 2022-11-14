package com.kw.kwdn.domain.penalty.repository;

import com.kw.kwdn.domain.penalty.PenaltyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PenaltyItemRepository extends JpaRepository<PenaltyItem, Long> {

    @Query("select p_item from PenaltyItem as p_item where p_item.creator.id=:userId")
    List<PenaltyItem> findAllByUserId(String userId);
}

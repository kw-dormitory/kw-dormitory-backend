package com.kw.kwdn.domain.anonymousparty.repository;

import com.kw.kwdn.domain.anonymousparty.AnonymousParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousPartyRepository extends JpaRepository<AnonymousParty, Long> {
}

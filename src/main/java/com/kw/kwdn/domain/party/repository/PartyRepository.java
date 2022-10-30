package com.kw.kwdn.domain.party.repository;

import com.kw.kwdn.domain.party.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long>, PartyRepositoryCustom {
}

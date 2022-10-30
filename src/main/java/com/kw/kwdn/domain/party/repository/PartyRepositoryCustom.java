package com.kw.kwdn.domain.party.repository;

import com.kw.kwdn.domain.party.Party;
import com.kw.kwdn.domain.party.dto.PartySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PartyRepositoryCustom {
    Page<Party> findAll(Pageable pageable, PartySearch partySearch);
}

package com.kw.kwdn.domain.party.repository;


import com.kw.kwdn.domain.party.Party;
import com.kw.kwdn.domain.party.dto.PartySearch;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.kw.kwdn.domain.party.QParty.party;

@Slf4j
@Repository
public class PartyRepositoryImpl extends QuerydslRepositorySupport implements PartyRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public PartyRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Party.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<Party> findAll(Pageable pageable, PartySearch partySearch) {

        String searchContent = partySearch.getSearchContent() + "%";

        log.info("search content : " + searchContent);

        JPAQuery<Party> query = jpaQueryFactory
                .select(party)
                .from(party)
                .where(party.title
                        .like(searchContent));

        Long totalCount = jpaQueryFactory.select(party.count())
                .from(party)
                .where(party.title
                        .like(searchContent))
                .fetchOne();

        if (totalCount == null) throw new IllegalStateException("total count 조회에 오류가 생겼습니다.");
        List<Party> partyList = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(partyList, pageable, totalCount);
    }
}

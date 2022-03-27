package com.temple.manager.prayer.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.temple.manager.prayer.dto.PrayerTypeGroupCntDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.temple.manager.prayer.entity.QPrayer.prayer;

@Repository
@RequiredArgsConstructor
public class PrayerRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<PrayerTypeGroupCntDTO> getPrayersTypeGroupCnt(){
        return queryFactory.select(
                Projections.fields(PrayerTypeGroupCntDTO.class,
                        prayer.code.codeName.max().as("prayerName"),
                        prayer.code.count().as("prayerCnt")))
                .from(prayer)
                .innerJoin(prayer.code)
                .groupBy(prayer.code)
                .orderBy(prayer.code.count().desc())
                .setHint(QueryHints.READ_ONLY, true)
                .fetch();
    }
}

package com.temple.manager.prayer.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.temple.manager.prayer.dto.PrayerGridListDTO;
import com.temple.manager.prayer.dto.PrayerTypeGroupCntDTO;
import com.temple.manager.prayer.dto.QPrayerGridListDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.temple.manager.believer.entity.QBeliever.believer;
import static com.temple.manager.code.entity.QCode.code;
import static com.temple.manager.prayer.entity.QPrayer.prayer;

@Repository
@RequiredArgsConstructor
public class PrayerRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public List<PrayerGridListDTO> getPrayers(){
        return queryFactory
                .select(
                        new QPrayerGridListDTO(
                                prayer.prayerId,
                                prayer.prayerStartDate,
                                believer.believerId,
                                believer.birthOfYear,
                                believer.believerName,
                                code.codeId,
                                code.codeName)
                )
                .from(prayer)
                .innerJoin(believer).on(prayer.believerId.eq(believer.believerId))
                .innerJoin(code).on(prayer.prayerTypeCodeId.eq(code.codeId))
                .fetch();
    }

    public List<PrayerGridListDTO> getPrayersByBelieverId(long believerId){
        return queryFactory
                .select(
                        new QPrayerGridListDTO(
                                prayer.prayerId,
                                prayer.prayerStartDate,
                                believer.believerId,
                                believer.birthOfYear,
                                believer.believerName,
                                code.codeId,
                                code.codeName)
                )
                .from(prayer)
                .innerJoin(believer).on(prayer.believerId.eq(believer.believerId))
                .innerJoin(code).on(prayer.prayerTypeCodeId.eq(code.codeId))
                .where(believer.believerId.eq(believerId))
                .fetch();
    }

    public List<PrayerTypeGroupCntDTO> getPrayersTypeGroupCnt(){
        return queryFactory.select(
                Projections.fields(PrayerTypeGroupCntDTO.class,
                        code.codeName.max().as("prayerName"),
                        code.count().as("prayerCnt")))
                .from(prayer)
                .innerJoin(code).on(prayer.prayerTypeCodeId.eq(code.codeId))
                .groupBy(code)
                .orderBy(code.count().desc())
                .setHint(QueryHints.READ_ONLY, true)
                .fetch();
    }
}

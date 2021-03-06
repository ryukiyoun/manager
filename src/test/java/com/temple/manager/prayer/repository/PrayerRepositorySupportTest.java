package com.temple.manager.prayer.repository;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.code.entity.Code;
import com.temple.manager.code.repository.CodeRepository;
import com.temple.manager.common.config.QuerydslConfig;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.prayer.dto.PrayerGridListDTO;
import com.temple.manager.prayer.dto.PrayerTypeGroupCntDTO;
import com.temple.manager.prayer.entity.Prayer;
import com.temple.manager.util.AesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, QuerydslConfig.class, RedisProperties.class, PrayerRepositorySupport.class})
@DataJpaTest
@WithMockUser(username = "user")
public class PrayerRepositorySupportTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    PrayerRepository prayerRepository;

    @Autowired
    BelieverRepository believerRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    PrayerRepositorySupport prayerRepositorySupport;

    Believer saveBeliever;

    @BeforeEach
    void init(){
        saveBeliever = Believer.builder()
                .believerName("saveTester")
                .birthOfYear("111111")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("??????")
                .build();

        believerRepository.save(saveBeliever);

        Code code1 = Code.builder()
                .codeName("??????1")
                .codeValue("prayer1")
                .parentCodeValue("P_PRAYER_TYPE")
                .build();

        Code code2 = Code.builder()
                .codeName("??????2")
                .codeValue("prayer2")
                .parentCodeValue("P_PRAYER_TYPE")
                .build();

        codeRepository.save(code1);
        codeRepository.save(code2);

        prayerRepository.save(Prayer.builder()
                .prayerStartDate(LocalDate.now())
                .believerId(saveBeliever.getBelieverId())
                .prayerTypeCodeId(code1.getCodeId())
                .build());

        prayerRepository.save(Prayer.builder()
                .prayerStartDate(LocalDate.now())
                .believerId(saveBeliever.getBelieverId())
                .prayerTypeCodeId(code2.getCodeId())
                .build());

        prayerRepository.save(Prayer.builder()
                .prayerStartDate(LocalDate.now())
                .believerId(saveBeliever.getBelieverId())
                .prayerTypeCodeId(code2.getCodeId())
                .build());

        em.clear();
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void getPrayers() {
        //when
        List<PrayerGridListDTO> result = prayerRepositorySupport.getPrayers();

        //then
        assertThat(result.size(), is(3));
    }

    @Test
    @DisplayName("?????? ?????? ??? ?????? ?????? Id??? ?????? ?????????")
    void getPrayersByBelieverId() {
        //when
        List<PrayerGridListDTO> result = prayerRepositorySupport.getPrayersByBelieverId(saveBeliever.getBelieverId());

        //then
        assertThat(result.size(), is(3));
    }

    @Test
    @DisplayName("?????? ????????? ?????? ????????? ?????? ?????????")
    void getPrayersTypeGroupCnt() {
        //when
        List<PrayerTypeGroupCntDTO> result = prayerRepositorySupport.getPrayersTypeGroupCnt();

        //then
        assertThat(result.size(), is(2));
    }
}

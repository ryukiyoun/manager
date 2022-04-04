package com.temple.manager.repository;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.code.entity.Code;
import com.temple.manager.code.repository.CodeRepository;
import com.temple.manager.config.QuerydslConfig;
import com.temple.manager.config.RedisConfig;
import com.temple.manager.config.RedisProperties;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.prayer.dto.PrayerTypeGroupCntDTO;
import com.temple.manager.prayer.entity.Prayer;
import com.temple.manager.prayer.repository.PrayerRepository;
import com.temple.manager.prayer.repository.PrayerRepositorySupport;
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

    @BeforeEach
    void init(){
        Believer saveBeliever = Believer.builder()
                .believerName("saveTester")
                .birthOfYear("111111")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("부산")
                .build();

        believerRepository.save(saveBeliever);

        Code code1 = Code.builder()
                .codeName("기도1")
                .codeValue("prayer1")
                .parentCodeValue("P_PRAYER_TYPE")
                .build();

        Code code2 = Code.builder()
                .codeName("기도2")
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
    @DisplayName("등록 기도에 대한 기도별 갯수 테스트")
    void getPrayersTypeGroupCnt() {
        List<PrayerTypeGroupCntDTO> result = prayerRepositorySupport.getPrayersTypeGroupCnt();

        assertThat(result.size(), is(2));
    }
}

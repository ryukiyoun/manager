package com.temple.manager.believer.repository;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.enumable.LunarSolarType;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, RedisProperties.class})
@DataJpaTest
@WithMockUser(username = "user")
public class BelieverRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    BelieverRepository believerRepository;

    Believer saveBeliever;

    @BeforeEach
    void init(){
        saveBeliever = Believer.builder()
                .believerName("saveTester")
                .birthOfYear("111111")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("부산")
                .build();

        believerRepository.save(saveBeliever);

        em.clear();
    }

    @Test
    @DisplayName("등록된 신도 중 특정 이름으로 조회")
    void getBelieversByNameTest() {
        //when
        List<Believer> result = believerRepository.findAllByBelieverNameContains("save");

        //then
        assertThat(result.get(0).getBelieverId(), is(not(0)));
        assertThat(result.get(0).getBelieverName(), is("saveTester"));
        assertThat(result.get(0).getBirthOfYear(), is("111111"));
        assertThat(result.get(0).getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.get(0).getAddress(), is("부산"));
        assertThat(result.get(0).getActive(), is("99999999999999"));
        assertThat(result.get(0).getCreateBy(), is("user"));
        assertThat(result.get(0).getUpdateBy(), is("user"));
        assertThat(result.get(0).getCreateDate(), is(not(nullValue())));
        assertThat(result.get(0).getUpdateDate(), is(not(nullValue())));
    }

    @Test
    @DisplayName("등록된 신도 중 특정 이름으로 조회")
    void getBelieversByNameAndBirthOfYearTest() {
        //when
        Optional<Believer> result = believerRepository.findAllByBelieverNameAndBirthOfYear("saveTester", "111111");

        //then
        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getBelieverId(), is(not(0)));
        assertThat(result.get().getBelieverName(), is("saveTester"));
        assertThat(result.get().getBirthOfYear(), is("111111"));
        assertThat(result.get().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.get().getAddress(), is("부산"));
        assertThat(result.get().getActive(), is("99999999999999"));
        assertThat(result.get().getCreateBy(), is("user"));
        assertThat(result.get().getUpdateBy(), is("user"));
        assertThat(result.get().getCreateDate(), is(not(nullValue())));
        assertThat(result.get().getUpdateDate(), is(not(nullValue())));
    }

    @Test
    @DisplayName("등록된 신도 중 특정 이름으로 조회 미등록 신도")
    void getBelieversByNameAndBirthOfYearTestThrowException() {
        //when
        Optional<Believer> result = believerRepository.findAllByBelieverNameAndBirthOfYear("saveTester", "123123");

        //then
        assertThat(result.isPresent(), is(false));
    }

    @Test
    @DisplayName("등록된 신도 중 최신등록 신도 5건 조회")
    void getRecent5Believers() {
        //when
        List<Believer> result = believerRepository.findTop5ByOrderByBelieverIdDesc();

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getBelieverId(), is(not(0)));
        assertThat(result.get(0).getBelieverName(), is("saveTester"));
        assertThat(result.get(0).getBirthOfYear(), is("111111"));
        assertThat(result.get(0).getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.get(0).getAddress(), is("부산"));
        assertThat(result.get(0).getActive(), is("99999999999999"));
        assertThat(result.get(0).getCreateBy(), is("user"));
        assertThat(result.get(0).getUpdateBy(), is("user"));
        assertThat(result.get(0).getCreateDate(), is(not(nullValue())));
        assertThat(result.get(0).getUpdateDate(), is(not(nullValue())));
    }

    @Test
    @DisplayName("신도 추가 테스트")
    void saveBelieverTest() {
        //given
        Believer saveBeliever = Believer.builder()
                .believerName("saveTester2")
                .birthOfYear("222222")
                .lunarSolarType(LunarSolarType.SOLAR)
                .address("서울")
                .build();

        //when
        Believer result = believerRepository.save(saveBeliever);

        //when
        assertThat(result.getBelieverId(), is(not(0)));
        assertThat(result.getBelieverName(), is("saveTester2"));
        assertThat(result.getBirthOfYear(), is("222222"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.SOLAR));
        assertThat(result.getAddress(), is("서울"));
        assertThat(result.getActive(), is("99999999999999"));
        assertThat(result.getCreateBy(), is("user"));
        assertThat(result.getUpdateBy(), is("user"));
        assertThat(result.getCreateDate(), is(not(nullValue())));
        assertThat(result.getUpdateDate(), is(not(nullValue())));
    }

    @Test
    @DisplayName("신도 변경 테스트")
    @Transactional
    void updateBelieverTest() {
        //given
        Believer saveBeliever = Believer.builder()
                .believerName("saveTester2")
                .birthOfYear("222222")
                .lunarSolarType(LunarSolarType.SOLAR)
                .address("서울")
                .build();

        //when
        Believer result = believerRepository.save(saveBeliever);

        result.update(BelieverDTO.builder()
                .believerName("updateTester")
                .birthOfYear("123123")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("서울")
                .build());

        //then
        assertThat(result.getBelieverId(), is(not(0)));
        assertThat(result.getBelieverName(), is("updateTester"));
        assertThat(result.getBirthOfYear(), is("123123"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getAddress(), is("서울"));
        assertThat(result.getActive(), is("99999999999999"));
        assertThat(result.getCreateBy(), is("user"));
        assertThat(result.getUpdateBy(), is("user"));
        assertThat(result.getCreateDate(), is(not(nullValue())));
        assertThat(result.getUpdateDate(), is(not(nullValue())));
    }

    @Test
    @DisplayName("신도 삭제 테스트")
    @Transactional
    void deleteBelieverTest() {
        //when
        believerRepository.deleteById(saveBeliever.getBelieverId());

        Believer result = believerRepository.findById(saveBeliever.getBelieverId()).orElse(null);

        //then
        assertThat(result, is(nullValue()));
    }
}

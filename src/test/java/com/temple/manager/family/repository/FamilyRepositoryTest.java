package com.temple.manager.family.repository;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.entity.Family;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, RedisProperties.class})
@DataJpaTest
@WithMockUser(username = "user")
public class FamilyRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    FamilyRepository familyRepository;

    @Autowired
    BelieverRepository believerRepository;

    Family family;

    Believer believer;

    @BeforeEach
    void init(){
        believer = Believer.builder()
                .believerName("tester")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("testerAddress")
                .birthOfYear("660412")
                .build();

        believerRepository.save(believer);

        family = Family.builder()
                .believerId(believer.getBelieverId())
                .familyName("testerFamily")
                .birthOfYear("710924")
                .familyType(FamilyType.FATHER)
                .lunarSolarType(LunarSolarType.LUNAR)
                .build();

        familyRepository.save(family);

        em.clear();
    }

    @Test
    @DisplayName("등록 가족 중 신도Id로 조회")
    void findAllByBeliever_BelieverId(){
        //when
        List<Family> result = familyRepository.findAllByBeliever_BelieverId(believer.getBelieverId());

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getBelieverId(), is(believer.getBelieverId()));
        assertThat(result.get(0).getFamilyName(), is("testerFamily"));
        assertThat(result.get(0).getBirthOfYear(), is("710924"));
        assertThat(result.get(0).getFamilyType(), is(FamilyType.FATHER));
        assertThat(result.get(0).getLunarSolarType(), is(LunarSolarType.LUNAR));
    }
}

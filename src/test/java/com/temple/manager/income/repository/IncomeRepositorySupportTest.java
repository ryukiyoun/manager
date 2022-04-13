package com.temple.manager.income.repository;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.code.entity.Code;
import com.temple.manager.code.repository.CodeRepository;
import com.temple.manager.common.config.QuerydslConfig;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.income.dto.IncomeGridListDTO;
import com.temple.manager.income.entity.Income;
import com.temple.manager.util.AesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, QuerydslConfig.class, RedisProperties.class, IncomeRepositorySupport.class})
@DataJpaTest
@WithMockUser(username = "user")
public class IncomeRepositorySupportTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    BelieverRepository believerRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    IncomeRepositorySupport incomeRepositorySupport;

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

        Code code1 = Code.builder()
                .codeName("수입1")
                .codeValue("income1")
                .parentCodeValue("P_INCOME_TYPE")
                .build();

        Code code2 = Code.builder()
                .codeName("수입2")
                .codeValue("income2")
                .parentCodeValue("P_INCOME_TYPE")
                .build();

        codeRepository.save(code1);
        codeRepository.save(code2);

        incomeRepository.save(Income.builder()
                .believerId(saveBeliever.getBelieverId())
                .paymentType(PaymentType.BELIEVER)
                .cashAmount(100)
                .cardAmount(100)
                .bankBookAmount(100)
                .installment(1)
                .incomeDate(LocalDate.now())
                .incomeTypeCodeId(code1.getCodeId())
                .build());

        incomeRepository.save(Income.builder()
                .paymentType(PaymentType.TEMPLE)
                .cashAmount(200)
                .cardAmount(200)
                .bankBookAmount(200)
                .installment(12)
                .incomeDate(LocalDate.now())
                .incomeTypeCodeId(code2.getCodeId())
                .build());

        em.clear();
    }

    @Test
    @DisplayName("등록 수입 조회 테스트")
    void getIncomes(){
        //when
        List<IncomeGridListDTO> result = incomeRepositorySupport.getIncomes();

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getBelieverName(), is("saveTester"));
        assertThat(result.get(0).getCodeName(), is("수입1"));
        assertThat(result.get(0).getCashAmount(), is(100L));
        assertThat(result.get(0).getCardAmount(), is(100L));
        assertThat(result.get(0).getBankBookAmount(), is(100L));
        assertThat(result.get(0).getInstallment(), is(1));
        assertThat(result.get(0).getPaymentType(), is(PaymentType.BELIEVER));
        assertThat(result.get(0).getIncomeDate(), is(LocalDate.now()));

        assertThat(result.get(1).getBelieverName(), is("해심"));
        assertThat(result.get(1).getCodeName(), is("수입2"));
        assertThat(result.get(1).getCashAmount(), is(200L));
        assertThat(result.get(1).getCardAmount(), is(200L));
        assertThat(result.get(1).getBankBookAmount(), is(200L));
        assertThat(result.get(1).getInstallment(), is(12));
        assertThat(result.get(1).getPaymentType(), is(PaymentType.TEMPLE));
        assertThat(result.get(1).getIncomeDate(), is(LocalDate.now()));
    }

    @Test
    @DisplayName("등록 수입 신도Id로 조회 테스트")
    void getIncomeByBelieverId(){
        //when
        List<IncomeGridListDTO> result = incomeRepositorySupport.getIncomeByBelieverId(saveBeliever.getBelieverId());

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getBelieverName(), is("saveTester"));
        assertThat(result.get(0).getCodeName(), is("수입1"));
        assertThat(result.get(0).getCashAmount(), is(100L));
        assertThat(result.get(0).getCardAmount(), is(100L));
        assertThat(result.get(0).getBankBookAmount(), is(100L));
        assertThat(result.get(0).getInstallment(), is(1));
        assertThat(result.get(0).getPaymentType(), is(PaymentType.BELIEVER));
        assertThat(result.get(0).getIncomeDate(), is(LocalDate.now()));
    }

    @Test
    @DisplayName("등록 수입 최신 등록순으로 조회 테스트")
    void getIncomeTop5(){
        //when
        List<IncomeGridListDTO> result = incomeRepositorySupport.getIncomeTop5(PageRequest.of(0, 5));

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getBelieverName(), is("해심"));
        assertThat(result.get(0).getCodeName(), is("수입2"));
        assertThat(result.get(0).getCashAmount(), is(200L));
        assertThat(result.get(0).getCardAmount(), is(200L));
        assertThat(result.get(0).getBankBookAmount(), is(200L));
        assertThat(result.get(0).getInstallment(), is(12));
        assertThat(result.get(0).getPaymentType(), is(PaymentType.TEMPLE));
        assertThat(result.get(0).getIncomeDate(), is(LocalDate.now()));

        assertThat(result.get(1).getBelieverName(), is("saveTester"));
        assertThat(result.get(1).getCodeName(), is("수입1"));
        assertThat(result.get(1).getCashAmount(), is(100L));
        assertThat(result.get(1).getCardAmount(), is(100L));
        assertThat(result.get(1).getBankBookAmount(), is(100L));
        assertThat(result.get(1).getInstallment(), is(1));
        assertThat(result.get(1).getPaymentType(), is(PaymentType.BELIEVER));
        assertThat(result.get(1).getIncomeDate(), is(LocalDate.now()));
    }
}

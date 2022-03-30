package com.temple.manager.repository;

import com.temple.manager.believer.entity.Believer;
import com.temple.manager.believer.repository.BelieverRepository;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.code.repository.CodeRepository;
import com.temple.manager.config.RedisConfig;
import com.temple.manager.config.RedisProperties;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.expenditure.dto.ExpenditureDTO;
import com.temple.manager.expenditure.entity.Expenditure;
import com.temple.manager.expenditure.repository.ExpenditureRepository;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, RedisProperties.class})
@DataJpaTest
@WithMockUser(username = "user")
public class ExpenditureRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    ExpenditureRepository expenditureRepository;

    @Autowired
    BelieverRepository believerRepository;

    @Autowired
    CodeRepository codeRepository;

    Expenditure saveExpenditure, saveExpenditure2;

    Believer saveBeliever;

    Code saveCode;

    @BeforeEach
    void init(){
        saveCode = Code.builder()
                .codeName("testN")
                .codeValue("testV")
                .parentCodeValue("P_T")
                .build();

        codeRepository.save(saveCode);

        saveBeliever = Believer.builder()
                .believerName("saveTester")
                .birthOfYear("111111")
                .lunarSolarType(LunarSolarType.LUNAR)
                .address("부산")
                .build();

        believerRepository.save(saveBeliever);

        saveExpenditure = Expenditure.builder()
                .expenditureDate(LocalDate.now())
                .believer(saveBeliever)
                .cashAmount(100L)
                .cardAmount(100L)
                .bankBookAmount(100L)
                .installment(1)
                .code(saveCode)
                .paymentType(PaymentType.BELIEVER)
                .build();

        saveExpenditure2 = Expenditure.builder()
                .expenditureDate(LocalDate.now())
                .cashAmount(100L)
                .cardAmount(100L)
                .bankBookAmount(100L)
                .installment(1)
                .code(saveCode)
                .paymentType(PaymentType.BELIEVER)
                .build();

        expenditureRepository.save(saveExpenditure);

        expenditureRepository.save(saveExpenditure2);

        em.clear();
    }

    @Test
    @DisplayName("등록된 지출 조회 테스트")
    void getAllExpenditures() {
        //when
        List<Expenditure> result = expenditureRepository.findAll();

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getExpenditureDate(), is(LocalDate.now()));
        assertThat(result.get(0).getBeliever(), is(not(nullValue())));
        assertThat(result.get(0).getCashAmount(), is(100L));
        assertThat(result.get(0).getCardAmount(), is(100L));
        assertThat(result.get(0).getBankBookAmount(), is(100L));
        assertThat(result.get(0).getInstallment(), is(1));
        assertThat(result.get(0).getCode(), is(not(nullValue())));
        assertThat(result.get(0).getPaymentType(), is(PaymentType.BELIEVER));
    }

    @Test
    @DisplayName("등록된 지출 중 특정 신도로 조회 테스트")
    void getExpendituresByBelieverId() {
        //when
        List<Expenditure> result = expenditureRepository.findAllByBeliever_BelieverId(saveBeliever.getBelieverId());

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getExpenditureDate(), is(LocalDate.now()));
        assertThat(result.get(0).getBeliever(), is(not(nullValue())));
        assertThat(result.get(0).getCashAmount(), is(100L));
        assertThat(result.get(0).getCardAmount(), is(100L));
        assertThat(result.get(0).getBankBookAmount(), is(100L));
        assertThat(result.get(0).getInstallment(), is(1));
        assertThat(result.get(0).getCode(), is(not(nullValue())));
        assertThat(result.get(0).getPaymentType(), is(PaymentType.BELIEVER));
    }

    @Test
    @DisplayName("등록된 지출 중 최근 등록된 지출 5건 조회 테스트")
    void getRecent5Expenditures() {
        //when
        List<Expenditure> result = expenditureRepository.findTop5ByOrderByExpenditureIdDesc(PageRequest.of(0, 5));

        //then
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getExpenditureDate(), is(LocalDate.now()));
        assertThat(result.get(0).getBeliever(), is(not(nullValue())));
        assertThat(result.get(0).getCashAmount(), is(100L));
        assertThat(result.get(0).getCardAmount(), is(100L));
        assertThat(result.get(0).getBankBookAmount(), is(100L));
        assertThat(result.get(0).getInstallment(), is(1));
        assertThat(result.get(0).getCode(), is(not(nullValue())));
        assertThat(result.get(0).getPaymentType(), is(PaymentType.BELIEVER));
    }

    @Test
    @DisplayName("지출 추가 테스트 테스트")
    void setSaveExpenditureTest() {
        //given
        Expenditure saveExpenditure = Expenditure.builder()
                .expenditureDate(LocalDate.now())
                .believer(saveBeliever)
                .cashAmount(200L)
                .cardAmount(200L)
                .bankBookAmount(200L)
                .installment(2)
                .code(saveCode)
                .paymentType(PaymentType.TEMPLE)
                .build();

        //when
        Expenditure result = expenditureRepository.save(saveExpenditure);

        //when
        assertThat(result.getExpenditureId(), is(not(nullValue())));
        assertThat(result.getExpenditureDate(), is(LocalDate.now()));
        assertThat(result.getBeliever(), is(not(nullValue())));
        assertThat(result.getCashAmount(), is(200L));
        assertThat(result.getCardAmount(), is(200L));
        assertThat(result.getBankBookAmount(), is(200L));
        assertThat(result.getInstallment(), is(2));
        assertThat(result.getCode(), is(not(nullValue())));
        assertThat(result.getPaymentType(), is(PaymentType.TEMPLE));
    }

    @Test
    @DisplayName("지출 변경 테스트")
    @Transactional
    void updateExpenditureTest() {
        //given
        Expenditure saveExpenditure = Expenditure.builder()
                .expenditureDate(LocalDate.now())
                .believer(saveBeliever)
                .cashAmount(200L)
                .cardAmount(200L)
                .bankBookAmount(200L)
                .installment(2)
                .code(saveCode)
                .paymentType(PaymentType.TEMPLE)
                .build();

        //when
        Expenditure result = expenditureRepository.save(saveExpenditure);

        result.update(ExpenditureDTO.builder()
                .expenditureDate(LocalDate.now())
                .cashAmount(300L)
                .cardAmount(300L)
                .bankBookAmount(300L)
                .installment(2)
                .code(CodeDTO.builder().codeId(saveCode.getCodeId()).build())
                .paymentType(PaymentType.BELIEVER)
                .build());

        //then
        assertThat(result.getExpenditureId(), is(not(nullValue())));
        assertThat(result.getExpenditureDate(), is(LocalDate.now()));
        assertThat(result.getBeliever(), is(nullValue()));
        assertThat(result.getCashAmount(), is(300L));
        assertThat(result.getCardAmount(), is(300L));
        assertThat(result.getBankBookAmount(), is(300L));
        assertThat(result.getInstallment(), is(2));
        assertThat(result.getCode(), is(not(nullValue())));
        assertThat(result.getPaymentType(), is(PaymentType.TEMPLE));
    }

    @Test
    @DisplayName("지출 삭제 테스트")
    @Transactional
    void deleteBelieverTest() {
        //when
        expenditureRepository.deleteById(saveExpenditure.getExpenditureId());

        Expenditure result = expenditureRepository.findById(saveExpenditure.getExpenditureId()).orElse(null);

        //then
        assertThat(result, is(nullValue()));
    }
}

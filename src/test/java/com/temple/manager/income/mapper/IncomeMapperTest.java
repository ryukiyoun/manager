package com.temple.manager.income.mapper;

import com.temple.manager.common.config.TestMapperConfig;
import com.temple.manager.enumable.PaymentType;
import com.temple.manager.income.dto.IncomeDTO;
import com.temple.manager.income.entity.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestMapperConfig.class})
public class IncomeMapperTest {
    @Autowired
    IncomeMapper incomeMapper;

    Income fixture;

    IncomeDTO fixtureDTO;

    List<Income> fixtureList;

    List<IncomeDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture = Income.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(100)
                .cardAmount(200)
                .bankBookAmount(300)
                .installment(3)
                .paymentType(PaymentType.BELIEVER)
                .believerId(1L)
                .incomeTypeCodeId(1)
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture);

        fixtureDTO = IncomeDTO.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(100)
                .cardAmount(200)
                .bankBookAmount(300)
                .installment(3)
                .paymentType(PaymentType.BELIEVER)
                .believerId(1L)
                .incomeTypeCodeId(1)
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO);
    }

    @Test
    @DisplayName("수입 Entity에서 DTO로 변경 테스트")
    void entityToDTO(){
        //when
        IncomeDTO result = incomeMapper.entityToDTO(fixture);

        //then
        assertThat(result.getIncomeId(), is(1L));
        assertThat(result.getIncomeDate(), is(LocalDate.now()));
        assertThat(result.getCashAmount(), is(100L));
        assertThat(result.getCardAmount(), is(200L));
        assertThat(result.getBankBookAmount(), is(300L));
        assertThat(result.getInstallment(), is(3));
        assertThat(result.getPaymentType(), is(PaymentType.BELIEVER));

        assertThat(result.getBelieverId(), is(1L));

        assertThat(result.getIncomeTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("수입 Entity에서 DTO로 변경 테스트 Entity Null")
    void entityToDTONull(){
        //when
        IncomeDTO result = incomeMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("수입 Entity에서 DTO로 변경 테스트 Believer Null")
    void entityToDTOBelieverNull(){
        //given
        Income fixture = Income.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(100)
                .cardAmount(200)
                .bankBookAmount(300)
                .installment(3)
                .paymentType(PaymentType.BELIEVER)
                .believerId(null)
                .incomeTypeCodeId(1)
                .build();

        //when
        IncomeDTO result = incomeMapper.entityToDTO(fixture);

        //then
        assertThat(result.getIncomeId(), is(1L));
        assertThat(result.getIncomeDate(), is(LocalDate.now()));
        assertThat(result.getCashAmount(), is(100L));
        assertThat(result.getCardAmount(), is(200L));
        assertThat(result.getBankBookAmount(), is(300L));
        assertThat(result.getInstallment(), is(3));
        assertThat(result.getPaymentType(), is(PaymentType.BELIEVER));

        assertThat(result.getBelieverId(), is(nullValue()));

        assertThat(result.getIncomeTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("수입 Entity에서 DTO로 변경 테스트 Code Null")
    void entityToDTOCodeNull(){
        //given
        Income fixture = Income.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(100)
                .cardAmount(200)
                .bankBookAmount(300)
                .installment(3)
                .paymentType(PaymentType.BELIEVER)
                .believerId(1L)
                .build();

        //when
        IncomeDTO result = incomeMapper.entityToDTO(fixture);

        //then
        assertThat(result.getIncomeId(), is(1L));
        assertThat(result.getIncomeDate(), is(LocalDate.now()));
        assertThat(result.getCashAmount(), is(100L));
        assertThat(result.getCardAmount(), is(200L));
        assertThat(result.getBankBookAmount(), is(300L));
        assertThat(result.getInstallment(), is(3));
        assertThat(result.getPaymentType(), is(PaymentType.BELIEVER));

        assertThat(result.getBelieverId(), is(1L));

        assertThat(result.getIncomeTypeCodeId(), is(0L));
    }

    @Test
    @DisplayName("수입 Entity에서 DTO로 변경 테스트")
    void DTOToEntity(){
        //when
        Income result = incomeMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getIncomeId(), is(1L));
        assertThat(result.getIncomeDate(), is(LocalDate.now()));
        assertThat(result.getCashAmount(), is(100L));
        assertThat(result.getCardAmount(), is(200L));
        assertThat(result.getBankBookAmount(), is(300L));
        assertThat(result.getInstallment(), is(3));
        assertThat(result.getPaymentType(), is(PaymentType.BELIEVER));

        assertThat(result.getBelieverId(), is(1L));

        assertThat(result.getIncomeTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("수입 Entity에서 DTO로 변경 테스트 Entity Null")
    void DTOToEntityNull(){
        //when
        Income result = incomeMapper.DTOToEntity(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("수입 DTO에서 Entity로 변경 테스트 Believer Null")
    void DTOToEntityBelieverNull(){
        //given
        IncomeDTO fixture = IncomeDTO.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(100)
                .cardAmount(200)
                .bankBookAmount(300)
                .installment(3)
                .paymentType(PaymentType.BELIEVER)
                .incomeTypeCodeId(1)
                .build();

        //when
        Income result = incomeMapper.DTOToEntity(fixture);

        //then
        assertThat(result.getIncomeId(), is(1L));
        assertThat(result.getIncomeDate(), is(LocalDate.now()));
        assertThat(result.getCashAmount(), is(100L));
        assertThat(result.getCardAmount(), is(200L));
        assertThat(result.getBankBookAmount(), is(300L));
        assertThat(result.getInstallment(), is(3));
        assertThat(result.getPaymentType(), is(PaymentType.BELIEVER));

        assertThat(result.getBelieverId(), is(nullValue()));

        assertThat(result.getIncomeTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("수입 DTO에서 Entity로 변경 테스트 Code Null")
    void DTOToEntityCodeNull(){
        //given
        IncomeDTO fixture = IncomeDTO.builder()
                .incomeId(1)
                .incomeDate(LocalDate.now())
                .cashAmount(100)
                .cardAmount(200)
                .bankBookAmount(300)
                .installment(3)
                .paymentType(PaymentType.BELIEVER)
                .believerId(1L)
                .build();

        //when
        Income result = incomeMapper.DTOToEntity(fixture);

        //then
        assertThat(result.getIncomeId(), is(1L));
        assertThat(result.getIncomeDate(), is(LocalDate.now()));
        assertThat(result.getCashAmount(), is(100L));
        assertThat(result.getCardAmount(), is(200L));
        assertThat(result.getBankBookAmount(), is(300L));
        assertThat(result.getInstallment(), is(3));
        assertThat(result.getPaymentType(), is(PaymentType.BELIEVER));

        assertThat(result.getBelieverId(), is(1L));

        assertThat(result.getIncomeTypeCodeId(), is(0L));
    }

    @Test
    @DisplayName("수입 EntityList에서 DTOList로 변경 테스트")
    void entityListToDTOList(){
        //when
        List<IncomeDTO> result = incomeMapper.entityListToDTOList(fixtureList);

        //then
        assertThat(result.get(0).getIncomeId(), is(1L));
        assertThat(result.get(0).getIncomeDate(), is(LocalDate.now()));
        assertThat(result.get(0).getCashAmount(), is(100L));
        assertThat(result.get(0).getCardAmount(), is(200L));
        assertThat(result.get(0).getBankBookAmount(), is(300L));
        assertThat(result.get(0).getInstallment(), is(3));
        assertThat(result.get(0).getPaymentType(), is(PaymentType.BELIEVER));

        assertThat(result.get(0).getBelieverId(), is(1L));

        assertThat(result.get(0).getIncomeTypeCodeId(), is(1L));
    }

    @Test
    @DisplayName("수입 EntityList에서 DTOList로 변경 테스트 Entity Null")
    void entityListToDTOListNull(){
        //when
        List<IncomeDTO> result = incomeMapper.entityListToDTOList(null);

        //then
        assertThat(result, is(nullValue()));
    }
}

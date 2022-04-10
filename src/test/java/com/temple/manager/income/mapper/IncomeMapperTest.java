package com.temple.manager.income.mapper;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.common.config.TestMapperConfig;
import com.temple.manager.enumable.LunarSolarType;
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
                .believer(Believer.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("서울")
                        .build())
                .code(Code.builder()
                        .codeId(1)
                        .codeName("testCode")
                        .codeValue("testValue")
                        .parentCodeValue("P_TEST")
                        .att1("1")
                        .att2("2")
                        .att3("3")
                        .build())
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
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("서울")
                        .build())
                .code(CodeDTO.builder()
                        .codeId(1)
                        .codeName("testCode")
                        .codeValue("testValue")
                        .parentCodeValue("P_TEST")
                        .att1("1")
                        .att2("2")
                        .att3("3")
                        .build())
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

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("서울"));

        assertThat(result.getCode().getCodeId(), is(1L));
        assertThat(result.getCode().getCodeName(), is("testCode"));
        assertThat(result.getCode().getCodeValue(), is("testValue"));
        assertThat(result.getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.getCode().getAtt1(), is("1"));
        assertThat(result.getCode().getAtt2(), is("2"));
        assertThat(result.getCode().getAtt3(), is("3"));
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
                .believer(null)
                .code(Code.builder()
                        .codeId(1)
                        .codeName("testCode")
                        .codeValue("testValue")
                        .parentCodeValue("P_TEST")
                        .att1("1")
                        .att2("2")
                        .att3("3")
                        .build())
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

        assertThat(result.getBeliever(), is(nullValue()));

        assertThat(result.getCode().getCodeId(), is(1L));
        assertThat(result.getCode().getCodeName(), is("testCode"));
        assertThat(result.getCode().getCodeValue(), is("testValue"));
        assertThat(result.getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.getCode().getAtt1(), is("1"));
        assertThat(result.getCode().getAtt2(), is("2"));
        assertThat(result.getCode().getAtt3(), is("3"));
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
                .believer(Believer.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("서울")
                        .build())
                .code(null)
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

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("서울"));

        assertThat(result.getCode(), is(nullValue()));
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

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("서울"));

        assertThat(result.getCode().getCodeId(), is(1L));
        assertThat(result.getCode().getCodeName(), is("testCode"));
        assertThat(result.getCode().getCodeValue(), is("testValue"));
        assertThat(result.getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.getCode().getAtt1(), is("1"));
        assertThat(result.getCode().getAtt2(), is("2"));
        assertThat(result.getCode().getAtt3(), is("3"));
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
                .believer(null)
                .code(CodeDTO.builder()
                        .codeId(1)
                        .codeName("testCode")
                        .codeValue("testValue")
                        .parentCodeValue("P_TEST")
                        .att1("1")
                        .att2("2")
                        .att3("3")
                        .build())
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

        assertThat(result.getBeliever(), is(nullValue()));

        assertThat(result.getCode().getCodeId(), is(1L));
        assertThat(result.getCode().getCodeName(), is("testCode"));
        assertThat(result.getCode().getCodeValue(), is("testValue"));
        assertThat(result.getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.getCode().getAtt1(), is("1"));
        assertThat(result.getCode().getAtt2(), is("2"));
        assertThat(result.getCode().getAtt3(), is("3"));
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
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("서울")
                        .build())
                .code(null)
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

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("서울"));

        assertThat(result.getCode(), is(nullValue()));
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

        assertThat(result.get(0).getBeliever().getBelieverId(), is(1L));
        assertThat(result.get(0).getBeliever().getBelieverName(), is("tester"));
        assertThat(result.get(0).getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.get(0).getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.get(0).getBeliever().getAddress(), is("서울"));

        assertThat(result.get(0).getCode().getCodeId(), is(1L));
        assertThat(result.get(0).getCode().getCodeName(), is("testCode"));
        assertThat(result.get(0).getCode().getCodeValue(), is("testValue"));
        assertThat(result.get(0).getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.get(0).getCode().getAtt1(), is("1"));
        assertThat(result.get(0).getCode().getAtt2(), is("2"));
        assertThat(result.get(0).getCode().getAtt3(), is("3"));
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

package com.temple.manager.mapper;

import com.temple.manager.believer.dto.BelieverDTO;
import com.temple.manager.believer.entity.Believer;
import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.config.TestMapperConfig;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.prayer.dto.PrayerDTO;
import com.temple.manager.prayer.entity.Prayer;
import com.temple.manager.prayer.mapper.PrayerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestMapperConfig.class})
public class PrayerMapperTest {
    @Autowired
    PrayerMapper prayerMapper;

    Prayer fixture;

    PrayerDTO fixtureDTO;

    List<Prayer> fixtureList;

    List<PrayerDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believer(Believer.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("부산")
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

        fixtureDTO = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("부산")
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
    @DisplayName("가족 Entity에서 DTO로 변경 테스트")
    void entityToDTO(){
        //when
        PrayerDTO result = prayerMapper.entityToDTO(fixture);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("부산"));

        assertThat(result.getCode().getCodeId(), is(1L));
        assertThat(result.getCode().getCodeName(), is("testCode"));
        assertThat(result.getCode().getCodeValue(), is("testValue"));
        assertThat(result.getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.getCode().getAtt1(), is("1"));
        assertThat(result.getCode().getAtt2(), is("2"));
        assertThat(result.getCode().getAtt3(), is("3"));
    }

    @Test
    @DisplayName("가족 Entity에서 DTO로 변경 테스트 Entity Null")
    void entityToDTONull(){
        //when
        PrayerDTO result = prayerMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Believer Null")
    void entityToDTOBelieverNull(){
        //given
        Prayer fixture = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
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
        PrayerDTO result = prayerMapper.entityToDTO(fixture);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

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
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Code Null")
    void entityToDTOCodeNull(){
        //given
        Prayer fixture = Prayer.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believer(Believer.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("부산")
                        .build())
                .code(null)
                .build();

        //when
        PrayerDTO result = prayerMapper.entityToDTO(fixture);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("부산"));

        assertThat(result.getCode(), is(nullValue()));
    }

    @Test
    @DisplayName("가족 Entity에서 DTO로 변경 테스트")
    void DTOToEntity(){
        //when
        Prayer result = prayerMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("부산"));

        assertThat(result.getCode().getCodeId(), is(1L));
        assertThat(result.getCode().getCodeName(), is("testCode"));
        assertThat(result.getCode().getCodeValue(), is("testValue"));
        assertThat(result.getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.getCode().getAtt1(), is("1"));
        assertThat(result.getCode().getAtt2(), is("2"));
        assertThat(result.getCode().getAtt3(), is("3"));
    }

    @Test
    @DisplayName("가족 Entity에서 DTO로 변경 테스트 Entity Null")
    void DTOToEntityNull(){
        //when
        Prayer result = prayerMapper.DTOToEntity(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Believer Null")
    void DTOToEntityBelieverNull(){
        //given
        PrayerDTO fixtureDTO = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
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
        Prayer result = prayerMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

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
    @DisplayName("기도 Entity에서 DTO로 변경 테스트 Code Null")
    void DTOToEntityCodeNull(){
        //given
        PrayerDTO fixtureDTO = PrayerDTO.builder()
                .prayerId(1)
                .prayerStartDate(LocalDate.now())
                .believer(BelieverDTO.builder()
                        .believerId(1)
                        .believerName("tester")
                        .birthOfYear("111111")
                        .lunarSolarType(LunarSolarType.LUNAR)
                        .address("부산")
                        .build())
                .code(null)
                .build();

        //when
        Prayer result = prayerMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getPrayerId(), is(1L));
        assertThat(result.getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.getBeliever().getBelieverId(), is(1L));
        assertThat(result.getBeliever().getBelieverName(), is("tester"));
        assertThat(result.getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getBeliever().getAddress(), is("부산"));

        assertThat(result.getCode(), is(nullValue()));
    }

    @Test
    @DisplayName("기도 EntityList에서 DTOList로 변경 테스트")
    void entityListToDTOList(){
        //when
        List<PrayerDTO> result = prayerMapper.entityListToDTOList(fixtureList);

        //then
        assertThat(result.get(0).getPrayerId(), is(1L));
        assertThat(result.get(0).getPrayerStartDate(), is(LocalDate.now()));

        assertThat(result.get(0).getBeliever().getBelieverId(), is(1L));
        assertThat(result.get(0).getBeliever().getBelieverName(), is("tester"));
        assertThat(result.get(0).getBeliever().getBirthOfYear(), is("111111"));
        assertThat(result.get(0).getBeliever().getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.get(0).getBeliever().getAddress(), is("부산"));

        assertThat(result.get(0).getCode().getCodeId(), is(1L));
        assertThat(result.get(0).getCode().getCodeName(), is("testCode"));
        assertThat(result.get(0).getCode().getCodeValue(), is("testValue"));
        assertThat(result.get(0).getCode().getParentCodeValue(), is("P_TEST"));
        assertThat(result.get(0).getCode().getAtt1(), is("1"));
        assertThat(result.get(0).getCode().getAtt2(), is("2"));
        assertThat(result.get(0).getCode().getAtt3(), is("3"));
    }

    @Test
    @DisplayName("기도 EntityList에서 DTOList로 변경 테스트 Entity Null")
    void entityListToDTOListNull(){
        //when
        List<PrayerDTO> result = prayerMapper.entityListToDTOList(null);

        //then
        assertThat(result, is(nullValue()));
    }
}

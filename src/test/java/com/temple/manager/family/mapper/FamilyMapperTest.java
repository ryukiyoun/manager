package com.temple.manager.family.mapper;

import com.temple.manager.common.config.TestMapperConfig;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.family.entity.Family;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {TestMapperConfig.class})
public class FamilyMapperTest {
    @Autowired
    FamilyMapper familyMapper;

    Family fixture;

    FamilyDTO fixtureDTO;

    List<Family> fixtureList;

    List<FamilyDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture = Family.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believerId(1)
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture);

        fixtureDTO = FamilyDTO.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believerId(1)
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO);
    }

    @Test
    @DisplayName("?????? Entity?????? DTO??? ?????? ?????????")
    void entityToDTO(){
        //when
        FamilyDTO result = familyMapper.entityToDTO(fixture);

        //then
        assertThat(result.getFamilyId(), is(1L));
        assertThat(result.getFamilyName(), is("tester1"));
        assertThat(result.getBirthOfYear(), is("111111"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getFamilyType(), is(FamilyType.FATHER));
        assertThat(result.getEtcValue(), is("etc1"));
        assertThat(result.getBelieverId(), is(1L));
    }

    @Test
    @DisplayName("?????? Entity?????? DTO??? ?????? ????????? Entity Null")
    void entityToDTONull(){
        //when
        FamilyDTO result = familyMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("?????? Entity?????? DTO??? ?????? ????????? Believer Null")
    void entityToDTOBelieverNull(){
        //given
        Family fixture = Family.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believerId(0L)
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();
        //when
        FamilyDTO result = familyMapper.entityToDTO(fixture);

        //then
        assertThat(result.getFamilyId(), is(1L));
        assertThat(result.getFamilyName(), is("tester1"));
        assertThat(result.getBirthOfYear(), is("111111"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getFamilyType(), is(FamilyType.FATHER));
        assertThat(result.getEtcValue(), is("etc1"));
    }

    @Test
    @DisplayName("?????? DTO?????? Entity??? ?????? ?????????")
    void DTOToEntity(){
        //when
        Family result = familyMapper.DTOToEntity(fixtureDTO);

        //then
        assertThat(result.getFamilyId(), is(1L));
        assertThat(result.getFamilyName(), is("tester1"));
        assertThat(result.getBirthOfYear(), is("111111"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getFamilyType(), is(FamilyType.FATHER));
        assertThat(result.getEtcValue(), is("etc1"));
        assertThat(result.getBelieverId(), is(1L));
    }

    @Test
    @DisplayName("?????? DTO?????? Entity??? ?????? ????????? Entity Null")
    void DTOToEntityNull(){
        //when
        Family result = familyMapper.DTOToEntity(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("?????? DTO?????? Entity??? ?????? ????????? Believer Null")
    void DTOToEntityBelieverNull(){
        //given
        FamilyDTO fixture = FamilyDTO.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believerId(0L)
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();
        //when
        Family result = familyMapper.DTOToEntity(fixture);

        //then
        assertThat(result.getFamilyId(), is(1L));
        assertThat(result.getFamilyName(), is("tester1"));
        assertThat(result.getBirthOfYear(), is("111111"));
        assertThat(result.getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.getFamilyType(), is(FamilyType.FATHER));
        assertThat(result.getEtcValue(), is("etc1"));
    }

    @Test
    @DisplayName("?????? EntityList?????? DTOList??? ?????? ?????????")
    void entityListToDTOList(){
        //when
        List<FamilyDTO> result = familyMapper.entityListToDTOList(fixtureList);

        //then
        assertThat(result.get(0).getFamilyId(), is(1L));
        assertThat(result.get(0).getFamilyName(), is("tester1"));
        assertThat(result.get(0).getBirthOfYear(), is("111111"));
        assertThat(result.get(0).getLunarSolarType(), is(LunarSolarType.LUNAR));
        assertThat(result.get(0).getFamilyType(), is(FamilyType.FATHER));
        assertThat(result.get(0).getEtcValue(), is("etc1"));
        assertThat(result.get(0).getBelieverId(), is(1L));
    }

    @Test
    @DisplayName("?????? EntityList?????? DTOList??? ?????? ????????? Entity Null")
    void entityListToDTOListNull(){
        //when
        List<FamilyDTO> result = familyMapper.entityListToDTOList(null);

        //then
        assertThat(result, is(nullValue()));
    }
}

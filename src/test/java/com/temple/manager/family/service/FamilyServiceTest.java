package com.temple.manager.family.service;

import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.family.dto.FamilyDTO;
import com.temple.manager.family.entity.Family;
import com.temple.manager.family.mapper.FamilyMapper;
import com.temple.manager.family.repository.FamilyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FamilyServiceTest {
    @Mock
    FamilyRepository familyRepository;

    @Mock
    FamilyMapper familyMapper;

    @InjectMocks
    FamilyService familyService;

    Family fixture1, fixture2;

    FamilyDTO fixtureDTO1, fixtureDTO2;

    List<Family> fixtureList;

    List<FamilyDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture1 = Family.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believerId(1)
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();

        fixture2 = Family.builder()
                .familyId(2)
                .familyName("tester2")
                .familyType(FamilyType.MOTHER)
                .believerId(1)
                .lunarSolarType(LunarSolarType.SOLAR)
                .birthOfYear("222222")
                .etcValue("etc2")
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        fixtureDTO1 = FamilyDTO.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believerId(1)
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();

        fixtureDTO2 = FamilyDTO.builder()
                .familyId(2)
                .familyName("tester2")
                .familyType(FamilyType.MOTHER)
                .believerId(1)
                .lunarSolarType(LunarSolarType.SOLAR)
                .birthOfYear("222222")
                .etcValue("etc2")
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO1);
        fixtureDTOList.add(fixtureDTO2);
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ??? Entity?????? DTO ???????????? ?????? ?????????")
    void getFamiliesByBelieverId() {
        //given
        given(familyRepository.findAllByBeliever_BelieverId(anyLong())).willReturn(fixtureList);
        given(familyMapper.entityListToDTOList(anyList())).willReturn(fixtureDTOList);

        //when
        List<FamilyDTO> result = familyService.getFamiliesByBelieverId(1);

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void appendFamily() {
        //given
        given(familyRepository.save(any(Family.class))).willReturn(fixture1);
        given(familyMapper.DTOToEntity(any(FamilyDTO.class))).willReturn(fixture1);
        given(familyMapper.entityToDTO(any(Family.class))).willReturn(fixtureDTO1);

        //when
        FamilyDTO result = familyService.appendFamily(fixtureDTO1);

        //then
        checkEntity(result, fixtureDTO1);
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void updateFamily() {
        //given
        Family spyFamily = spy(Family.class);

        given(familyRepository.findById(anyLong())).willReturn(Optional.of(spyFamily));

        //when
        familyService.updateFamily(1, FamilyDTO.builder().build());

        //then
        verify(familyRepository, times(1)).findById(anyLong());
        verify(spyFamily, times(1)).update(any(FamilyDTO.class));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ??? Empty ?????????")
    void updateFamilyEmpty(){
        //given
        given(familyRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () ->familyService.updateFamily(1, FamilyDTO.builder().build()));
    }

    @Test
    @DisplayName("?????? ?????? Soft Delete ?????????")
    void deleteFamily() {
        //when
        familyService.deleteFamily(1);

        //then
        verify(familyRepository, times(1)).deleteById(anyLong());
    }

    void checkEntity(FamilyDTO resultDTO, FamilyDTO compareDTO){
        assertThat(resultDTO.getFamilyId(), is(compareDTO.getFamilyId()));
        assertThat(resultDTO.getFamilyName(), is(compareDTO.getFamilyName()));
        assertThat(resultDTO.getFamilyType(), is(compareDTO.getFamilyType()));
        assertThat(resultDTO.getLunarSolarType(), is(compareDTO.getLunarSolarType()));
        assertThat(resultDTO.getBirthOfYear(), is(compareDTO.getBirthOfYear()));
        assertThat(resultDTO.getEtcValue(), is(compareDTO.getEtcValue()));
        assertThat(resultDTO.getBelieverId(), is(compareDTO.getBelieverId()));
    }
}
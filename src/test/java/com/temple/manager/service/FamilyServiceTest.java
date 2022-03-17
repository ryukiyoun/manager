package com.temple.manager.service;

import com.temple.manager.dto.FamilyDTO;
import com.temple.manager.entity.Believer;
import com.temple.manager.entity.Family;
import com.temple.manager.enumable.FamilyType;
import com.temple.manager.enumable.LunarSolarType;
import com.temple.manager.repository.FamilyRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FamilyServiceTest {
    @Mock
    FamilyRepository familyRepository;

    @InjectMocks
    FamilyService familyService;

    Family fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = Family.builder()
                .familyId(1)
                .familyName("tester1")
                .familyType(FamilyType.FATHER)
                .believer(Believer.builder()
                        .believerId(1)
                        .believerName("believer1")
                        .birthOfYear("111111")
                        .address("부산")
                        .build())
                .lunarSolarType(LunarSolarType.LUNAR)
                .birthOfYear("111111")
                .etcValue("etc1")
                .build();

        fixture2 = Family.builder()
                .familyId(2)
                .familyName("tester2")
                .familyType(FamilyType.MOTHER)
                .believer(Believer.builder()
                        .believerId(1)
                        .believerName("believer2")
                        .birthOfYear("222222")
                        .address("서울")
                        .build())
                .lunarSolarType(LunarSolarType.SOLAR)
                .birthOfYear("222222")
                .etcValue("etc2")
                .build();
    }

    @Test
    @DisplayName("등록된 모든 가족 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getFamiliesByBelieverId() {
        //given
        List<Family> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(familyRepository.findAllByBeliever_BelieverId(anyLong())).willReturn(fixtureList);

        //when
        List<FamilyDTO> result = familyService.getFamiliesByBelieverId(1);

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixture1);
        checkEntity(result.get(1), fixture2);
    }

    @Test
    @DisplayName("특정 가족 수정 테스트")
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
    @DisplayName("특정 가족 수정 조회 시 Empty 테스트")
    void updateFamilyEmpty(){
        //given
        given(familyRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () ->familyService.updateFamily(1, FamilyDTO.builder().build()));
    }

    @Test
    @DisplayName("특정 가족 Soft Delete 테스트")
    void deleteFamily() {
        //given
        Family spyFamily = spy(Family.class);

        given(familyRepository.findById(anyLong())).willReturn(Optional.of(spyFamily));

        //when
        familyService.deleteFamily(1);

        //then
        verify(familyRepository, times(1)).findById(anyLong());
        verify(spyFamily, times(1)).delete();
    }

    @Test
    @DisplayName("특정 가족 삭제 조회 시 Empty 테스트")
    void deleteFamilyEmpty(){
        //given
        given(familyRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () ->familyService.deleteFamily(1));
    }

    void checkEntity(FamilyDTO resultDTO, Family fixtureEntity){
        assertThat(resultDTO.getFamilyId(), is(fixtureEntity.getFamilyId()));
        assertThat(resultDTO.getFamilyName(), is(fixtureEntity.getFamilyName()));
        assertThat(resultDTO.getFamilyType(), is(fixtureEntity.getFamilyType()));
        assertThat(resultDTO.getLunarSolarType(), is(fixtureEntity.getLunarSolarType()));
        assertThat(resultDTO.getBirthOfYear(), is(fixtureEntity.getBirthOfYear()));
        assertThat(resultDTO.getEtcValue(), is(fixtureEntity.getEtcValue()));
        assertThat(resultDTO.getBeliever().getBelieverId(), is(fixtureEntity.getBeliever().getBelieverId()));
    }
}
package com.temple.manager.code.service;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.code.repository.CodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CodeServiceTest {
    @Mock
    CodeRepository codeRepository;

    @InjectMocks
    CodeService codeService;

    Code fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = Code.builder()
                .codeId(1)
                .codeName("testCode1")
                .codeValue("C-1")
                .parentCodeValue("P-1")
                .att1("a")
                .att2("b")
                .att3("c")
                .build();

        fixture2 = Code.builder()
                .codeId(2)
                .codeName("testCode2")
                .codeValue("C-2")
                .parentCodeValue("P-1")
                .att1("d")
                .att2("e")
                .att3("f")
                .build();
    }

    @Test
    @DisplayName("등록된 코드 중 특정 부모코드로 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getCodeByParentCodeValue() {
        //given
        List<Code> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(codeRepository.findAllByParentCodeValue(anyString())).willReturn(fixtureList);

        //when
        List<CodeDTO> result = codeService.getCodeByParentCodeValue("P-1");

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixture1);
        checkEntity(result.get(1), fixture2);
    }

    void checkEntity(CodeDTO resultDTO, Code fixtureEntity){
        assertThat(resultDTO.getCodeId(), is(fixtureEntity.getCodeId()));
        assertThat(resultDTO.getCodeName(), is(fixtureEntity.getCodeName()));
        assertThat(resultDTO.getCodeValue(), is(fixtureEntity.getCodeValue()));
        assertThat(resultDTO.getParentCodeValue(), is(fixtureEntity.getParentCodeValue()));
        assertThat(resultDTO.getAtt1(), is(fixtureEntity.getAtt1()));
        assertThat(resultDTO.getAtt2(), is(fixtureEntity.getAtt2()));
        assertThat(resultDTO.getAtt3(), is(fixtureEntity.getAtt3()));
    }
}
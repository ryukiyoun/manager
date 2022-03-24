package com.temple.manager.service;

import com.temple.manager.code.dto.CodeDTO;
import com.temple.manager.code.entity.Code;
import com.temple.manager.code.mapper.CodeMapper;
import com.temple.manager.code.repository.CodeRepository;
import com.temple.manager.code.service.CodeService;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CodeServiceTest {
    @Mock
    CodeRepository codeRepository;

    @Mock
    CodeMapper codeMapper;

    @InjectMocks
    CodeService codeService;

    Code fixture1, fixture2;

    CodeDTO fixtureDTO1, fixtureDTO2;

    List<Code> fixtureList;

    List<CodeDTO> fixtureListDTO;

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

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        fixtureDTO1 = CodeDTO.builder()
                .codeId(1)
                .codeName("testCode1")
                .codeValue("C-1")
                .parentCodeValue("P-1")
                .att1("a")
                .att2("b")
                .att3("c")
                .build();

        fixtureDTO2 = CodeDTO.builder()
                .codeId(2)
                .codeName("testCode2")
                .codeValue("C-2")
                .parentCodeValue("P-1")
                .att1("d")
                .att2("e")
                .att3("f")
                .build();

        fixtureListDTO = new ArrayList<>();
        fixtureListDTO.add(fixtureDTO1);
        fixtureListDTO.add(fixtureDTO2);
    }

    @Test
    @DisplayName("등록된 코드 중 특정 부모코드로 조회 후 Entity에서 DTO 타입으로 변경 테스트")
    void getCodeByParentCodeValue() {
        //given
        given(codeRepository.findAllByParentCodeValue(anyString())).willReturn(fixtureList);
        given(codeMapper.entityListToDTOList(anyList())).willReturn(fixtureListDTO);

        //when
        List<CodeDTO> result = codeService.getCodeByParentCodeValue("P-1");

        //then
        assertThat(result.size(), is(2));
        checkEntity(result.get(0), fixtureDTO1);
        checkEntity(result.get(1), fixtureDTO2);
    }

    void checkEntity(CodeDTO resultDTO, CodeDTO compareDTO){
        assertThat(resultDTO.getCodeId(), is(compareDTO.getCodeId()));
        assertThat(resultDTO.getCodeName(), is(compareDTO.getCodeName()));
        assertThat(resultDTO.getCodeValue(), is(compareDTO.getCodeValue()));
        assertThat(resultDTO.getParentCodeValue(), is(compareDTO.getParentCodeValue()));
        assertThat(resultDTO.getAtt1(), is(compareDTO.getAtt1()));
        assertThat(resultDTO.getAtt2(), is(compareDTO.getAtt2()));
        assertThat(resultDTO.getAtt3(), is(compareDTO.getAtt3()));
    }
}
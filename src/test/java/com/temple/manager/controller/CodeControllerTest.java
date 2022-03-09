package com.temple.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temple.manager.dto.CodeDTO;
import com.temple.manager.service.CodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = CodeController.class, includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
@WithMockUser(username = "user")
class CodeControllerTest {
    @MockBean
    CodeService codeService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    CodeDTO fixture1, fixture2;

    @BeforeEach
    void init(){
        fixture1 = CodeDTO.builder()
                .codeId(1)
                .codeName("testCode1")
                .codeValue("C-1")
                .parentCodeValue("P-1")
                .att1("t")
                .att2("a")
                .att3("b")
                .build();

        fixture2 = CodeDTO.builder()
                .codeId(2)
                .codeName("testCode2")
                .codeValue("C-2")
                .parentCodeValue("P-1")
                .att1("n")
                .att2("w")
                .att3("e")
                .build();
    }

    @Test
    @DisplayName("등록된 부모 코드 검색 정보 반환 테스트")
    void getCodesByParentCodeValue() throws Exception{
        //given
        List<CodeDTO> fixtureList = new ArrayList<>();
        fixtureList.add(fixture1);
        fixtureList.add(fixture2);

        given(codeService.getCodeByParentCodeValue(anyString())).willReturn(fixtureList);

        //when, then
        String content = objectMapper.writeValueAsString(fixtureList);

        mockMvc.perform(get("/code/P-1")
                .with(csrf()))
                .andExpect(content().json(content))
                .andDo(print());
    }
}
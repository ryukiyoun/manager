package com.temple.manager.config;

import com.temple.manager.believer.mapper.BelieverMapper;
import com.temple.manager.code.mapper.CodeMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestMapperConfig {
    @Bean
    public BelieverMapper believerMapper(){
        return Mappers.getMapper(BelieverMapper.class);
    }

    @Bean
    public CodeMapper codeMapper(){
        return Mappers.getMapper(CodeMapper.class);
    }
}

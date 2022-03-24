package com.temple.manager.config;

import com.temple.manager.believer.mapper.BelieverMapper;
import com.temple.manager.code.mapper.CodeMapper;
import com.temple.manager.family.mapper.FamilyMapper;
import com.temple.manager.income.mapper.IncomeMapper;
import com.temple.manager.prayer.mapper.PrayerMapper;
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

    @Bean
    public FamilyMapper familyMapper(){
        return Mappers.getMapper(FamilyMapper.class);
    }

    @Bean
    public PrayerMapper prayerMapper(){
        return Mappers.getMapper(PrayerMapper.class);
    }

    @Bean
    public IncomeMapper incomeMapper(){
        return Mappers.getMapper(IncomeMapper.class);
    }
}

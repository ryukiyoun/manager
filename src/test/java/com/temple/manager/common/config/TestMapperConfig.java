package com.temple.manager.common.config;

import com.temple.manager.believer.mapper.BelieverMapper;
import com.temple.manager.code.mapper.CodeMapper;
import com.temple.manager.expenditure.mapper.ExpenditureMapper;
import com.temple.manager.family.mapper.FamilyMapper;
import com.temple.manager.income.mapper.IncomeMapper;
import com.temple.manager.notification.mapper.NotificationMapper;
import com.temple.manager.prayer.mapper.PrayerMapper;
import com.temple.manager.user.mapper.UserMapper;
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

    @Bean
    public ExpenditureMapper expenditureMapper(){
        return Mappers.getMapper(ExpenditureMapper.class);
    }

    @Bean
    public UserMapper userMapper(){
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public NotificationMapper notificationMapper(){
        return Mappers.getMapper(NotificationMapper.class);
    }
}

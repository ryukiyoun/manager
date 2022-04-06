package com.temple.manager.mapper;

import com.temple.manager.config.TestMapperConfig;
import com.temple.manager.enumable.UserRole;
import com.temple.manager.user.dto.UserDTO;
import com.temple.manager.user.entity.User;
import com.temple.manager.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestMapperConfig.class})
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    User fixture;

    @BeforeEach
    void init(){
        fixture = User.builder()
                .userName("test")
                .password("test_password")
                .userRole(UserRole.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("유저 Entity에서 DTO로 변경 테스트")
    void entityToDTO(){
        //when
        UserDTO result = userMapper.entityToDTO(fixture);

        //then
        assertThat(result.getUsername(), is("test"));
        assertThat(result.getPassword(), is("test_password"));
        assertThat(result.getUserRole(), is(UserRole.ROLE_USER));
    }

    @Test
    @DisplayName("유저 Entity에서 DTO로 변경 테스트 Entity Null")
    void entityToDTONull(){
        //when
        UserDTO result = userMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }
}

package com.temple.manager.user.service;

import com.temple.manager.enumable.UserRole;
import com.temple.manager.user.dto.UserDTO;
import com.temple.manager.user.entity.User;
import com.temple.manager.user.mapper.UserMapper;
import com.temple.manager.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("사용자 조회 테스트")
    void loadUserByUsername(){
        //given
        given(userRepository.findAllByUserName(anyString()))
                .willReturn(Optional.of(User.builder()
                        .id(1)
                        .userName("tester")
                        .password("test_password")
                        .userRole(UserRole.ROLE_USER)
                        .build()));

        given(userMapper.entityToDTO(any(User.class))).willReturn(UserDTO.builder()
                .id(1)
                .userName("tester")
                .password("test_password")
                .userRole(UserRole.ROLE_USER)
                .build());

        //when
        UserDetails result = userService.loadUserByUsername("tester");

        //then
        String auth = result.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);

        assertThat(result.getUsername(), is("tester"));
        assertThat(result.getPassword(), is("test_password"));
        assertThat(auth, is(not(nullValue())));
        assertThat(auth, is("ROLE_USER"));
    }

    @Test
    @DisplayName("사용자 조회 Exception 테스트")
    void loadUserByUsernameException(){
        //given
        given(userRepository.findAllByUserName(anyString())).willReturn(Optional.empty());

        //when, then
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("tester"));
    }
}

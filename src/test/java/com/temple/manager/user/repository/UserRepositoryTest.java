package com.temple.manager.user.repository;

import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.enumable.UserRole;
import com.temple.manager.user.entity.User;
import com.temple.manager.util.AesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, RedisProperties.class})
@DataJpaTest
@WithMockUser(username = "user")
public class UserRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void init(){
        user = User.builder()
                .userName("tester")
                .password("testPass")
                .userRole(UserRole.ROLE_USER)
                .build();

        user = userRepository.save(user);

        em.clear();
    }

    @Test
    @DisplayName("사용자 조회 테스트")
    void findAllByUserName(){
        //when
        Optional<User> result = userRepository.findAllByUserName(user.getUserName());

        //then
        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUserName(), is("tester"));
        assertThat(result.get().getPassword(), is("testPass"));
        assertThat(result.get().getUserRole(), is(UserRole.ROLE_USER));
    }
}

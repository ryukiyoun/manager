package com.temple.manager.code.repository;

import com.temple.manager.code.entity.Code;
import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, RedisProperties.class})
@DataJpaTest
@WithMockUser(username = "user")
public class CodeRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    CodeRepository codeRepository;

    Code code;

    @BeforeEach
    void init(){
        code = Code.builder()
                .codeValue("testCode")
                .codeName("test")
                .parentCodeValue("test_P")
                .att1("att1")
                .att2("att2")
                .att3("att3")
                .build();

        codeRepository.save(code);

        em.clear();
    }

    @Test
    @DisplayName("")
    void findAllByParentCodeValue(){
        //when
        List<Code> result = codeRepository.findAllByParentCodeValue("test_P");

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getCodeValue(), is("testCode"));
        assertThat(result.get(0).getCodeName(), is("test"));
        assertThat(result.get(0).getParentCodeValue(), is("test_P"));
        assertThat(result.get(0).getAtt1(), is("att1"));
        assertThat(result.get(0).getAtt2(), is("att2"));
        assertThat(result.get(0).getAtt3(), is("att3"));
    }
}

package com.temple.manager.notification.repository;

import com.temple.manager.common.config.RedisConfig;
import com.temple.manager.common.config.RedisProperties;
import com.temple.manager.notification.entity.Notification;
import com.temple.manager.util.AesUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@Import({AesUtil.class, RedisConfig.class, RedisProperties.class})
@DataJpaTest
public class NotificationRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    NotificationRepository notificationRepository;

    @Test
    @DisplayName("요청 사항 저장 테스트")
    @WithMockUser(username = "user")
    void saveNotificationTest(){
        //given
        Notification fixture = Notification.builder()
                .requestMessage("test_request")
                .build();

        //when
        notificationRepository.save(fixture);

        //then
        assertThat(fixture.getNotificationId(), is(not(0L)));
        assertThat(fixture.getRequestMessage(), is("test_request"));
    }

    @Test
    @DisplayName("요청 사항 확인 테스트")
    void confirmNotificationTest(){
        //given
        Notification fixture = Notification.builder()
                .requestMessage("test_request")
                .build();

        //when
        notificationRepository.save(fixture);

        fixture.confirmNotification();

        em.flush();
        em.clear();

        Notification result = notificationRepository.findById(fixture.getNotificationId()).orElse(null);

        //then
        assertThat(result, is(not(nullValue())));
        assertThat(result.isConfirm(), is(true));
    }

    @Test
    @DisplayName("요청 사항 수정 미인증 테스트")
    void updateNotificationTest(){
        //given
        Notification fixture = Notification.builder()
                .requestMessage("test_request")
                .build();

        //when
        notificationRepository.save(fixture);

        fixture.responseRegistration("test_title", "test_message");

        em.flush();
        em.clear();

        Notification result = notificationRepository.findById(fixture.getNotificationId()).orElse(null);

        //then
        assertThat(result, is(not(nullValue())));
        assertThat(result.getNotificationId(), is(not(0L)));
        assertThat(result.getRequestMessage(), is("test_request"));
        assertThat(result.getResponseTitle(), is("test_title"));
        assertThat(result.getResponseMessage(), is("test_message"));
    }

    @Test
    @DisplayName("요청 사항 수정 Anonymous 테스트")
    @WithAnonymousUser
    void updateNotificationAnonymousTest(){
        //given
        Notification fixture = Notification.builder()
                .requestMessage("test_request")
                .build();

        //when
        notificationRepository.save(fixture);

        fixture.responseRegistration("test_title", "test_message");

        em.flush();
        em.clear();

        Notification result = notificationRepository.findById(fixture.getNotificationId()).orElse(null);

        //then
        assertThat(result, is(not(nullValue())));
        assertThat(result.getNotificationId(), is(not(0L)));
        assertThat(result.getRequestMessage(), is("test_request"));
        assertThat(result.getResponseTitle(), is("test_title"));
        assertThat(result.getResponseMessage(), is("test_message"));
    }
}

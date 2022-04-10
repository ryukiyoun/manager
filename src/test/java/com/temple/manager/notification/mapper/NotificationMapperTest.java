package com.temple.manager.notification.mapper;

import com.temple.manager.common.config.TestMapperConfig;
import com.temple.manager.notification.dto.NotificationDTO;
import com.temple.manager.notification.entity.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestMapperConfig.class})
public class NotificationMapperTest {
    @Autowired
    NotificationMapper notificationMapper;

    Notification fixture;

    NotificationDTO fixtureDTO;

    List<Notification> fixtureList;

    List<NotificationDTO> fixtureDTOList;

    @BeforeEach
    void init(){
        fixture = Notification.builder()
                .notificationId(1)
                .requestMessage("test")
                .responseTitle("title")
                .responseMessage("message")
                .confirm(true)
                .build();

        fixtureList = new ArrayList<>();
        fixtureList.add(fixture);

        fixtureDTO = NotificationDTO.builder()
                .notificationId(1)
                .requestMessage("test")
                .responseTitle("title")
                .responseMessage("message")
                .confirm(true)
                .build();

        fixtureDTOList = new ArrayList<>();
        fixtureDTOList.add(fixtureDTO);
    }

    @Test
    @DisplayName("Notification Entity에서 DTO로 변경 테스트")
    void entityToDTO(){
        //when
        NotificationDTO result = notificationMapper.entityToDTO(fixture);

        //then
        assertThat(result.getNotificationId(), is(1L));
        assertThat(result.getRequestMessage(), is("test"));
        assertThat(result.getResponseTitle(), is("title"));
        assertThat(result.getResponseMessage(), is("message"));
        assertThat(result.isConfirm(), is(true));
    }

    @Test
    @DisplayName("Notification Entity에서 DTO로 변경 테스트 Entity Null")
    void entityToDTONull(){
        //when
        NotificationDTO result = notificationMapper.entityToDTO(null);

        //then
        assertThat(result, is(nullValue()));
    }

    @Test
    @DisplayName("Notification EntityList에서 DTOList로 변경 테스트")
    void entityListToDTOList(){
        //when
        List<NotificationDTO> result = notificationMapper.entityListToDTOList(fixtureList);

        //then
        assertThat(result.get(0).getNotificationId(), is(1L));
        assertThat(result.get(0).getRequestMessage(), is("test"));
        assertThat(result.get(0).getResponseTitle(), is("title"));
        assertThat(result.get(0).getResponseMessage(), is("message"));
        assertThat(result.get(0).isConfirm(), is(true));
    }

    @Test
    @DisplayName("Notification EntityList에서 DTOList로 변경 테스트 Entity Null")
    void entityListToDTOListNull(){
        //when
        List<NotificationDTO> result = notificationMapper.entityListToDTOList(null);

        //then
        assertThat(result, is(nullValue()));
    }
}

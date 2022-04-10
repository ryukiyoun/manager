package com.temple.manager.notification.service;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.view.ViewState;
import com.temple.manager.notification.dto.NotificationDTO;
import com.temple.manager.notification.entity.Notification;
import com.temple.manager.notification.mapper.NotificationMapper;
import com.temple.manager.notification.repository.NotificationRepository;
import com.temple.manager.user.dto.UserDTO;
import com.temple.manager.util.SlackUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    @Mock
    NotificationRepository notificationRepository;

    @Mock
    SlackUtils slackUtils;

    @Mock
    NotificationMapper notificationMapper;

    @Spy
    @InjectMocks
    NotificationService notificationService;

    @Test
    @DisplayName("최근 등록 Notification 조회 테스트")
    void recentNotification() {
        //given
        List<Notification> fixture = new ArrayList<>();
        fixture.add(Notification.builder()
                .notificationId(1)
                .requestMessage("test")
                .responseTitle("title")
                .responseMessage("message")
                .confirm(false)
                .build());

        List<NotificationDTO> fixtureDTO = new ArrayList<>();
        fixtureDTO.add(NotificationDTO.builder()
                .notificationId(1)
                .requestMessage("test")
                .responseTitle("title")
                .responseMessage("message")
                .confirm(false)
                .build());

        given(notificationRepository.findAllByCreateByAndConfirmAndResponseMessageNotNull(anyString(), anyBoolean())).willReturn(fixture);
        given(notificationMapper.entityListToDTOList(anyList())).willReturn(fixtureDTO);

        //when
        List<NotificationDTO> result = notificationService.recentNotification(UserDTO.builder().userName("tester").build());

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getNotificationId(), is(1L));
        assertThat(result.get(0).getRequestMessage(), is("test"));
        assertThat(result.get(0).getResponseMessage(), is("message"));
        assertThat(result.get(0).getResponseTitle(), is("title"));
        assertThat(result.get(0).isConfirm(), is(false));
    }

    @Test
    @DisplayName("Notification 사용자 확인 테스트")
    void setNotificationConfirm() {
        //given
        Notification mockNotification = mock(Notification.class);
        given(notificationRepository.findById(anyLong())).willReturn(Optional.of(mockNotification));

        //when
        notificationService.setNotificationConfirm(1);

        //then
        verify(mockNotification, times(1)).confirmNotification();
    }

    @Test
    @DisplayName("Notification 사용자 확인 Empty 테스트")
    void setNotificationConfirmEmpty() {
        //given
        given(notificationRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> notificationService.setNotificationConfirm(1));
    }

    @Test
    @DisplayName("Slack 요청 Message 전송 테스트")
    void sendFunctionRequestMessage() {
        //given
        doReturn(1L).when(notificationService).functionRequestSave(anyString());
        doReturn(new ArrayList<>()).when(notificationService).buildFunctionRequestBlock(anyLong(), anyString());
        doNothing().when(slackUtils).sendSlackMessage(anyList());

        //when
        notificationService.sendFunctionRequestMessage("test");
    }

    @Test
    @DisplayName("Slack 요청 Message 생성 테스트")
    void buildFunctionRequestBlock() {
        //given
        given(slackUtils.getHeader(anyString())).willReturn(HeaderBlock.builder()
                .text(PlainTextObject.builder()
                        .text("header")
                        .build())
                .blockId("t_i1")
                .build());
        given(slackUtils.getSection(anyString())).willReturn(SectionBlock.builder()
                .text(PlainTextObject.builder()
                        .text("section")
                        .build())
                .blockId("t_i2")
                .build());
        given(slackUtils.getTextInput(anyString(), anyString())).willReturn(InputBlock.builder()
                .label(PlainTextObject.builder()
                        .text("input_param2")
                        .build())
                .blockId("t_i3")
                .build());
        given(slackUtils.getTextInput(anyString(), anyString(), anyString())).willReturn(InputBlock.builder()
                .label(PlainTextObject.builder()
                        .text("input_param3")
                        .build())
                .blockId("t_i4")
                .build());
        given(slackUtils.getTextArea(anyString(), anyString())).willReturn(InputBlock.builder()
                .label(PlainTextObject.builder()
                        .text("input_textarea")
                        .build())
                .blockId("t_i5")
                .build());
        given(slackUtils.getConfirmButtonBlocks()).willReturn(new ArrayList<>());

        //when
        List<LayoutBlock> result = notificationService.buildFunctionRequestBlock(1L, "test");

        //then
        assertThat(result.size(), is(7));
        assertThat(((HeaderBlock) result.get(0)).getBlockId(), is("t_i1"));
        assertThat(((HeaderBlock) result.get(0)).getText().getText(), is("header"));
        assertThat(((SectionBlock) result.get(1)).getBlockId(), is("t_i2"));
        assertThat(((PlainTextObject) ((SectionBlock) result.get(1)).getText()).getText(), is("section"));
        assertThat(((InputBlock) result.get(3)).getBlockId(), is("t_i4"));
        assertThat(((InputBlock) result.get(3)).getLabel().getText(), is("input_param3"));
        assertThat(((InputBlock) result.get(4)).getBlockId(), is("t_i3"));
        assertThat(((InputBlock) result.get(4)).getLabel().getText(), is("input_param2"));
        assertThat(((InputBlock) result.get(5)).getBlockId(), is("t_i5"));
        assertThat(((InputBlock) result.get(5)).getLabel().getText(), is("input_textarea"));
    }

    @Test
    @DisplayName("Notification 저장 테스트")
    void functionRequestSave() {
        //given
        given(notificationRepository.save(any(Notification.class))).willReturn(Notification.builder().notificationId(1L).build());

        //when
        long result = notificationService.functionRequestSave("test");

        //
        assertThat(result, is(1L));
    }

    @Test
    @DisplayName("Slack 답변 완료 Message 생성 테스트")
    void buildFunctionResponseMessage() {
        //given
        given(slackUtils.getHeader(anyString())).willReturn(HeaderBlock.builder()
                .text(PlainTextObject.builder()
                        .text("header")
                        .build())
                .blockId("t_i1")
                .build());
        given(slackUtils.getSection(anyString())).willReturn(SectionBlock.builder()
                .text(PlainTextObject.builder()
                        .text("section")
                        .build())
                .blockId("t_i2")
                .build());

        //when
        List<LayoutBlock> result = notificationService.buildFunctionResponseMessage();

        //then
        assertThat(result.size(), is(3));
        assertThat(((HeaderBlock) result.get(0)).getBlockId(), is("t_i1"));
        assertThat(((HeaderBlock) result.get(0)).getText().getText(), is("header"));
        assertThat(((SectionBlock) result.get(1)).getBlockId(), is("t_i2"));
        assertThat(((PlainTextObject) ((SectionBlock) result.get(1)).getText()).getText(), is("section"));
    }

    @Test
    @DisplayName("callback 결과 저장 후 Slack 메시지 전송 테스트")
    void sendResponseMessage() {
        //given
        Map<String, Map<String, ViewState.Value>> blockMap = mock(HashMap.class);
        Map<String, ViewState.Value> valueMap = mock(HashMap.class);

        ViewState.Value value = new ViewState.Value();
        value.setValue("1");

        given(slackUtils.getPayload(anyString())).willReturn(BlockActionPayload.builder().build());
        given(blockMap.get(anyString())).willReturn(valueMap);
        given(valueMap.get(anyString())).willReturn(value);

        doReturn(blockMap).when(notificationService).getMessageInputValue(any(BlockActionPayload.class));
        doNothing().when(notificationService).functionResponseSave(anyLong(), anyString(), anyString());

        //when
        notificationService.sendResponseMessage("test");
    }

    @Test
    @DisplayName("Slack callback 결과 저장 테스트")
    void functionResponseSave() {
        //given
        Notification notification = mock(Notification.class);
        given(notificationRepository.findById(anyLong())).willReturn(Optional.of(notification));

        //when
        notificationService.functionResponseSave(1, "test_title", "test_message");

        //then
        verify(notification, times(1)).responseRegistration(anyString(), anyString());
    }

    @Test
    @DisplayName("Slack callback 결과 저장 Exception 테스트")
    void functionResponseSaveException() {
        //given
        given(notificationRepository.findById(anyLong())).willReturn(Optional.empty());

        //when, then
        assertThrows(RuntimeException.class, () -> notificationService.functionResponseSave(1, "test_title", "test_message"));
    }

    @Test
    @DisplayName("callback 결과 input 값 추출 테스트")
    void getMessageInputValue() {
        //given
        Map<String, Map<String, ViewState.Value>> map = new HashMap<>();
        Map<String, ViewState.Value> t1 = new HashMap<>();

        ViewState.Value t1_value = new ViewState.Value();
        t1_value.setValue("test_value1");

        t1.put("t1_value", t1_value);

        map.put("t1", t1);

        BlockActionPayload blockActionPayload = BlockActionPayload.builder()
                .state(ViewState.builder()
                        .values(map)
                        .build())
                .build();

        //when
        Map<String, Map<String, ViewState.Value>> result = notificationService.getMessageInputValue(blockActionPayload);

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get("t1").size(), is(1));
        assertThat(result.get("t1").get("t1_value").getValue(), is("test_value1"));
    }
}

package com.temple.manager.util;

import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.ActionResponseSender;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ButtonElement;
import com.slack.api.model.block.element.PlainTextInputElement;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import com.temple.manager.config.ActionResponseSenderConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@Import({SlackUtils.class, ActionResponseSenderConfig.class})
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class SlackUtilsTest {
    @MockBean
    ActionResponseSender actionResponseSender;

    @Autowired
    SlackUtils slackUtils;

    @Test
    @DisplayName("Slack 요청 메시지 전송 테스트")
    void sendSlackMessage() throws Exception{
        //given
        Slack mockSlack = mock(Slack.class);
        MockedStatic<Slack> slackStatic = mockStatic(Slack.class);
        slackStatic.when(Slack::getInstance).thenReturn(mockSlack);

        given(mockSlack.send(anyString(), any(Payload.class))).willReturn(WebhookResponse.builder().build());

        //when
        slackUtils.sendSlackMessage(new ArrayList<>());

        //then
        verify(mockSlack, times(1)).send(anyString(), any(Payload.class));

        slackStatic.close();
    }

    @Test
    @DisplayName("Slack 요청 메시지 전송 Exception 테스트")
    void sendSlackMessageException() throws Exception{
        //given
        Slack mockSlack = mock(Slack.class);
        MockedStatic<Slack> slackStatic = mockStatic(Slack.class);
        slackStatic.when(Slack::getInstance).thenReturn(mockSlack);

        given(mockSlack.send(anyString(), any(Payload.class))).willThrow(new IOException());

        //when, then
        assertThrows(RuntimeException.class, () -> slackUtils.sendSlackMessage(new ArrayList<>()));

        slackStatic.close();
    }

    @Test
    @DisplayName("Slack 메시지 답변 callback 테스트")
    void sendResponseSlackMessage() throws Exception{
        //given
        Slack mockSlack = mock(Slack.class);
        MockedStatic<Slack> slackStatic = mockStatic(Slack.class);
        slackStatic.when(Slack::getInstance).thenReturn(mockSlack);

        given(actionResponseSender.send(anyString(), any(ActionResponse.class))).willReturn(WebhookResponse.builder().build());

        //when
        slackUtils.sendResponseSlackMessage("testUrl", new ArrayList<>());

        //then
        verify(actionResponseSender, times(1)).send(anyString(), any(ActionResponse.class));

        slackStatic.close();
    }

    @Test
    @DisplayName("Slack 메시지 답변 callback Exception 테스트")
    void sendResponseSlackMessageException() throws Exception{
        //given
        Slack mockSlack = mock(Slack.class);
        MockedStatic<Slack> slackStatic = mockStatic(Slack.class);
        slackStatic.when(Slack::getInstance).thenReturn(mockSlack);

        given(actionResponseSender.send(anyString(), any(ActionResponse.class))).willThrow(new IOException());

        //when, then
        assertThrows(RuntimeException.class, () -> slackUtils.sendResponseSlackMessage("testUrl", new ArrayList<>()));

        slackStatic.close();
    }

    @Test
    @DisplayName("Slack Header 생성 테스트")
    void getHeader(){
        //when
        HeaderBlock result = slackUtils.getHeader("t_h");

        //then
        assertThat(result.getText().getText(), is("t_h"));
        assertThat(result.getText().getEmoji(), is(true));
    }

    @Test
    @DisplayName("Slack Section 생성 테스트")
    void getSection(){
        //when
        SectionBlock result = slackUtils.getSection("t_s");

        //then
        assertThat(((MarkdownTextObject)result.getText()).getText(), is("t_s"));
    }

    @Test
    @DisplayName("Slack TestArea 생성 테스트")
    void getTextArea(){
        //when
        InputBlock result = slackUtils.getTextArea("t_l", "t_i");

        //then
        assertThat(result.getLabel().getText(), is("t_l"));
        assertThat(result.getBlockId(), is("t_i"));
    }

    @Test
    @DisplayName("Slack Input Param 2개 생성 테스트")
    void getTextInputPram2(){
        //when
        InputBlock result = slackUtils.getTextInput("t_l", "t_i");

        //then
        assertThat(result.getLabel().getText(), is("t_l"));
        assertThat(((PlainTextInputElement)result.getElement()).getInitialValue(), is(""));
        assertThat(result.getBlockId(), is("t_i"));
    }

    @Test
    @DisplayName("Slack Input Param 3개 생성 테스트")
    void getTextInputPram3(){
        //when
        InputBlock result = slackUtils.getTextInput("t_l", "t_i", "t_v");

        //then
        assertThat(result.getLabel().getText(), is("t_l"));
        assertThat(((PlainTextInputElement)result.getElement()).getInitialValue(), is("t_v"));
        assertThat(result.getBlockId(), is("t_i"));
    }

    @Test
    @DisplayName("Slack Action Button Group 생성 테스트")
    void getConfirmButtonBlocks(){
        //when
        List<BlockElement> result = slackUtils.getConfirmButtonBlocks();

        //then
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getClass(), is(ButtonElement.class));
        assertThat(((ButtonElement)result.get(0)).getText().getText(), is("전송"));
        assertThat(((ButtonElement)result.get(0)).getValue(), is("submit"));
        assertThat(((ButtonElement)result.get(0)).getStyle(), is("primary"));
        assertThat(((ButtonElement)result.get(0)).getActionId(), is("callback_submit"));
    }

    @Test
    @DisplayName("Slack Action Button 생성 테스트")
    void getActionButton(){
        //when
        BlockElement result = slackUtils.getActionButton("test_p", "t_p", "t_s", "t_a");

        //then
        assertThat(((ButtonElement)result).getText().getText(), is("test_p"));
        assertThat(((ButtonElement)result).getValue(), is("t_p"));
        assertThat(((ButtonElement)result).getStyle(), is("t_s"));
        assertThat(((ButtonElement)result).getActionId(), is("t_a"));
    }

    @Test
    @DisplayName("callback 객체 생성 테스트")
    void getPayload(){
        //when
        BlockActionPayload result = slackUtils.getPayload("{\"type\":\"block_actions\"}");

        //then
        assertThat(result.getClass(), is(BlockActionPayload.class));
        assertThat(result.getType(), is("block_actions"));
    }
}

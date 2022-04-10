package com.temple.manager.notification.service;

import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.view.ViewState.Value;
import com.temple.manager.notification.dto.NotificationDTO;
import com.temple.manager.notification.entity.Notification;
import com.temple.manager.notification.mapper.NotificationMapper;
import com.temple.manager.notification.repository.NotificationRepository;
import com.temple.manager.util.SlackUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final String SLACK_RESPONSE_TITLE = "response_title", SLACK_RESPONSE_MESSAGE = "response_message", SLACK_NOTIFICATION_ID = "response_id";

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final SlackUtils slackUtils;

    public List<NotificationDTO> recentNotification(UserDetails user){
        return notificationMapper.entityListToDTOList(notificationRepository.findAllByCreateByAndConfirmAndResponseMessageNotNull(user.getUsername(), false));
    }

    @Transactional
    public NotificationDTO setNotificationConfirm(long id){
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found Notification"));

        notification.confirmNotification();

        return notificationMapper.entityToDTO(notification);
    }

    public void sendFunctionRequestMessage(String message){
        long saveId = functionRequestSave(message);

        slackUtils.sendSlackMessage(buildFunctionRequestBlock(saveId, message));
    }

    public List<LayoutBlock> buildFunctionRequestBlock(long saveId, String message){
        return Blocks.asBlocks(
                slackUtils.getHeader("기능 요청~~"),
                slackUtils.getSection(message),
                Blocks.divider(),
                slackUtils.getTextInput("답변 ID", SLACK_NOTIFICATION_ID, Long.toString(saveId)),
                slackUtils.getTextInput("답변 제목", SLACK_RESPONSE_TITLE),
                slackUtils.getTextArea("답변 내용", SLACK_RESPONSE_MESSAGE),
                Blocks.actions(slackUtils.getConfirmButtonBlocks())
        );
    }

    @Transactional
    public void sendResponseMessage(String payloadJson) {
        BlockActionPayload blockPayload = slackUtils.getPayload(payloadJson);

        Map<String, Map<String, Value>> values = getMessageInputValue(blockPayload);

        functionResponseSave(Long.parseLong(values.get(SLACK_NOTIFICATION_ID).get(SLACK_NOTIFICATION_ID).getValue()),
                values.get(SLACK_RESPONSE_TITLE).get(SLACK_RESPONSE_TITLE).getValue(),
                values.get(SLACK_RESPONSE_MESSAGE).get(SLACK_RESPONSE_MESSAGE).getValue());

        slackUtils.sendResponseSlackMessage(blockPayload.getResponseUrl(), buildFunctionResponseMessage());
    }

    public void functionResponseSave(long id, String title, String message){
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found Notification"));

        notification.responseRegistration(title, message);
    }

    public long functionRequestSave(String message){
        Notification notification = notificationRepository.save(Notification.builder()
                .requestMessage(message)
                .build());

        return notification.getNotificationId();
    }

    public List<LayoutBlock> buildFunctionResponseMessage(){
        return Blocks.asBlocks(
                slackUtils.getHeader("답변 완료"),
                slackUtils.getSection("답변을 완료 하였습니다."),
                Blocks.divider()
        );
    }

    public Map<String, Map<String, Value>> getMessageInputValue(BlockActionPayload blockActionPayload){
        return blockActionPayload.getState().getValues();
    }
}

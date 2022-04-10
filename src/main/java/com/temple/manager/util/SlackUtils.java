package com.temple.manager.util;

import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.ActionResponseSender;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ButtonElement;
import com.slack.api.model.block.element.PlainTextInputElement;
import com.slack.api.util.json.GsonFactory;
import com.slack.api.webhook.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlackUtils {
    private final ActionResponseSender actionResponseSender;

    public void sendSlackMessage(List<LayoutBlock> layoutBlocks) {
        try {
            Slack.getInstance().send("https://hooks.slack.com/services/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",
                    Payload.builder().blocks(layoutBlocks).build());
        }
        catch (IOException e){
            throw new RuntimeException("Slack Request Message Send Error", e);
        }
    }

    public void sendResponseSlackMessage(String URL, List<LayoutBlock> layoutBlocks) {
        try {
            ActionResponse response = ActionResponse.builder()
                    .replaceOriginal(true)
                    .blocks(layoutBlocks)
                    .build();

            actionResponseSender.send(URL, response);
        }
        catch (IOException e){
            throw new RuntimeException("Slack Response Message Send Error", e);
        }
    }

    public HeaderBlock getHeader(String text) {
        return HeaderBlock.builder()
                .text(PlainTextObject.builder()
                        .text(text)
                        .emoji(true)
                        .build())
                .build();
    }

    public SectionBlock getSection(String message) {
        return SectionBlock.builder()
                .text(MarkdownTextObject.builder()
                        .text(message)
                        .build())
                .build();
    }

    public InputBlock getTextArea(String labelText, String actionId){
        return InputBlock.builder()
                .element(PlainTextInputElement.builder()
                        .actionId(actionId)
                        .multiline(true)
                        .build())
                .label(PlainTextObject.builder()
                        .text(labelText)
                        .build())
                .blockId(actionId)
                .build();
    }

    public InputBlock getTextInput(String labelText, String actionId){
        return getTextInput(labelText, actionId, "");
    }

    public InputBlock getTextInput(String labelText, String actionId, String value){
        return InputBlock.builder()
                .element(PlainTextInputElement.builder()
                        .actionId(actionId)
                        .initialValue(value)
                        .build())
                .label(PlainTextObject.builder()
                        .text(labelText)
                        .build())
                .blockId(actionId)
                .build();
    }

    public List<BlockElement> getConfirmButtonBlocks() {
        List<BlockElement> actions = new ArrayList<>();
        actions.add(getActionButton("전송", "submit", "primary", "callback_submit"));
        return actions;
    }

    public BlockElement getActionButton(String plainText, String value, String style, String actionId) {
        return ButtonElement.builder()
                .text(PlainTextObject.builder()
                        .text(plainText)
                        .emoji(true)
                        .build())
                .value(value)
                .style(style)
                .actionId(actionId)
                .build();
    }

    public BlockActionPayload getPayload(String payloadJson){
        return GsonFactory.createSnakeCase().fromJson(payloadJson, BlockActionPayload.class);
    }
}

package com.temple.manager.config;

import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.ActionResponseSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActionResponseSenderConfig {
    @Bean
    public ActionResponseSender actionResponseSender(){
        return new ActionResponseSender(Slack.getInstance());
    }
}

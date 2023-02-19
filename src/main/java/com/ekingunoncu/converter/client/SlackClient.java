package com.ekingunoncu.converter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "slack", url = "https://slack.com/api")
public interface SlackClient {

    @PostMapping("/chat.postMessage?channel={channel}&text={text}")
    void postMessage(   
        @RequestHeader("Authorization") String token,
                     @PathVariable("channel") String channel,
                     @PathVariable("text") String text);
}

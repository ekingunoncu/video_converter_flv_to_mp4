package com.ekingunoncu.converter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Feign client for the Slack API.
 */
@FeignClient(name = "slack", url = "https://slack.com/api")
public interface SlackClient {

    /**
     * Posts a message to the specified Slack channel.
     * 
     * @param token   The authorization token to use.
     * @param channel The Slack channel to post the message to.
     * @param text    The text of the message to post.
     */
    @PostMapping("/chat.postMessage?channel={channel}&text={text}")
    void postMessage(   
        @RequestHeader("Authorization") String token,
        @PathVariable("channel") String channel,
        @PathVariable("text") String text);
}

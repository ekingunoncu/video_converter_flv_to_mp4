package com.ekingunoncu.converter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ekingunoncu.converter.client.SlackClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackClient slackClient;

    @Value("${slack.token}")
    private String token; // holds the Slack API token

    @Value("${slack.channel}")
    private String channel; // holds the name of the Slack channel to post messages to

    /**
     * Sends a message to the Slack channel using the SlackClient.
     * 
     * @param text The message text to send.
     */
    public void sendMessage(String text) {
        slackClient.postMessage("Bearer " + token, channel, text);
    }
}

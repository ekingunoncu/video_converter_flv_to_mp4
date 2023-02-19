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
    private String token;

    @Value("${slack.channel}")
    private String channel;

    public void sendMessage(String text) {
        slackClient.postMessage("Bearer " + token, channel, text);
    }
}
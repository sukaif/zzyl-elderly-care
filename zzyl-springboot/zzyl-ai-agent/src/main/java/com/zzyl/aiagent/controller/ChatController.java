package com.zzyl.aiagent.controller;

import com.zzyl.common.annotation.Anonymous;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/aiconsult/virtualagent")
public class ChatController {

    @Autowired
    private ChatClient chatClient;

    /**
     * GetMapping 定义 SSE 端点。接收 prompt 参数，通过 chatClient 发起 AI 请求。
     * 利用 Flux 返回流式内容，将片段映射为 ServerSentEvent，实现实时推送 AI 回复。
     * @param prompt
     * @return
     */
    @Anonymous
    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content()
                //将内容封装成 ServerSentEvent
                //该类是 Spring 服务器发送事件（SSE）数据模型。包含 id、事件类型、重试时间及泛型数据负载等字段。采用 Builder 模式构建，提供属性获取、格式化方法，用于 HTTP 编解码处理 SSE 消息。
                .map(content -> ServerSentEvent.builder(content).build());
    }
}

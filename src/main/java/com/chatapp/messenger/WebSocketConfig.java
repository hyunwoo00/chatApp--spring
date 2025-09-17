package com.chatapp.messenger;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메세지 브로커 설정.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //서버 -> 클라이언트
        config.enableSimpleBroker("/topic");
        //클라이언트 -> 서버
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * STOMP 엔드포인트 등록.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") //클라이언트에서 접속할 엔드포인트
                .setAllowedOriginPatterns("*"); //cors 허용.
    }
    
}

package com.yunseong.notification.config;

import com.yunseong.common.AES256Util;
import com.yunseong.notification.messagehandlers.NotificationServiceEventConsumer;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.spring.events.subscriber.TramEventSubscriberConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.UnsupportedEncodingException;

@Configuration
@Import({TramEventSubscriberConfiguration.class})
public class NotificationServiceMessageHandlersConfiguration {

    @Bean
    public AES256Util aes256Util() throws UnsupportedEncodingException {
        return new AES256Util();
    }

    @Bean
    public NotificationServiceEventConsumer notificationServiceEventConsumer() {
        return new NotificationServiceEventConsumer();
    }

    @Bean
    public DomainEventDispatcher domainEventDispatcher(NotificationServiceEventConsumer notificationServiceEventConsumer, DomainEventDispatcherFactory domainEventDispatcherFactory) {
        return domainEventDispatcherFactory.make("notificationServiceEvents", notificationServiceEventConsumer.domainEventHandlers());
    }
}

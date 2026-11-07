package com.verizon.iot.billing.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic subscriptionCreatedTopic(@Value("${app.kafka.topics.subscription-created}") String name) {
        return TopicBuilder.name(name).partitions(6).replicas(3).build();
    }

    @Bean
    public NewTopic subscriptionTerminatedTopic(@Value("${app.kafka.topics.subscription-terminated}") String name) {
        return TopicBuilder.name(name).partitions(6).replicas(3).build();
    }

    @Bean
    public NewTopic invoiceGeneratedTopic(@Value("${app.kafka.topics.invoice-generated}") String name) {
        return TopicBuilder.name(name).partitions(3).replicas(3).build();
    }

    @Bean
    public RecordMessageConverter jsonMessageConverter() {
        return new StringJsonMessageConverter();
    }
}

package com.verizon.iot.billing.messaging;

import com.verizon.iot.billing.events.InvoiceGeneratedEvent;
import com.verizon.iot.billing.events.SubscriptionCreatedEvent;
import com.verizon.iot.billing.events.SubscriptionTerminatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillingEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topics.subscription-created}")
    private String subscriptionCreatedTopic;

    @Value("${app.kafka.topics.subscription-terminated}")
    private String subscriptionTerminatedTopic;

    @Value("${app.kafka.topics.invoice-generated}")
    private String invoiceGeneratedTopic;

    public void publishSubscriptionCreated(SubscriptionCreatedEvent event) {
        log.info("Publishing subscription-created orderId={}", event.orderId());
        kafkaTemplate.send(subscriptionCreatedTopic, event.orderId(), event);
    }

    public void publishSubscriptionTerminated(SubscriptionTerminatedEvent event) {
        log.info("Publishing subscription-terminated orderId={}", event.orderId());
        kafkaTemplate.send(subscriptionTerminatedTopic, event.orderId(), event);
    }

    public void publishInvoiceGenerated(InvoiceGeneratedEvent event) {
        log.info("Publishing invoice-generated account={}", event.accountNumber());
        kafkaTemplate.send(invoiceGeneratedTopic, event.accountNumber(), event);
    }
}

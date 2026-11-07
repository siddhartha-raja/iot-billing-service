package com.verizon.iot.billing.messaging;

import com.verizon.iot.billing.events.SimActivatedEvent;
import com.verizon.iot.billing.events.SimDeactivatedEvent;
import com.verizon.iot.billing.events.UsageRecordEvent;
import com.verizon.iot.billing.service.SubscriptionService;
import com.verizon.iot.billing.service.UsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/** Topic names here are string literals (style differs from other services on purpose). */
@Slf4j
@Component
@RequiredArgsConstructor
public class BillingEventConsumer {

    private final SubscriptionService subscriptionService;
    private final UsageService usageService;

    /** Step 4 of the flow: SIMs are live, so start billing. */
    @KafkaListener(topics = "iot.sim.activated", groupId = "iot-billing-service-group")
    public void onSimActivated(SimActivatedEvent event) {
        log.info("Received sim.activated orderId={}", event.orderId());
        subscriptionService.createSubscriptions(event);
    }

    @KafkaListener(topics = "iot.sim.deactivated", groupId = "iot-billing-service-group")
    public void onSimDeactivated(SimDeactivatedEvent event) {
        log.info("Received sim.deactivated orderId={}", event.orderId());
        subscriptionService.terminateSubscriptions(event);
    }

    /** Entry point from outside the platform (network mediation). */
    @KafkaListener(topics = "iot.usage.records", groupId = "iot-billing-service-group")
    public void onUsageRecord(UsageRecordEvent event) {
        usageService.recordUsage(event);
    }
}

package com.verizon.iot.billing.service;

import com.verizon.iot.billing.client.OrderClient;
import com.verizon.iot.billing.dto.OrderDetailsResponse;
import com.verizon.iot.billing.dto.SubscriptionResponse;
import com.verizon.iot.billing.entity.Subscription;
import com.verizon.iot.billing.entity.SubscriptionStatus;
import com.verizon.iot.billing.events.SimActivatedEvent;
import com.verizon.iot.billing.events.SimDeactivatedEvent;
import com.verizon.iot.billing.events.SubscriptionCreatedEvent;
import com.verizon.iot.billing.events.SubscriptionTerminatedEvent;
import com.verizon.iot.billing.messaging.BillingEventPublisher;
import com.verizon.iot.billing.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final OrderClient orderClient;
    private final BillingEventPublisher eventPublisher;

    /**
     * Create one subscription per activated SIM.
     *  1. enrich with order details (REST -> order-service)
     *  2. persist subscriptions
     *  3. emit subscription-created (Kafka) which lets order-service mark the order COMPLETED
     */
    @Transactional
    public void createSubscriptions(SimActivatedEvent event) {
        OrderDetailsResponse order = orderClient.getOrder(event.orderId());
        LocalDate start = LocalDate.now();

        List<String> subscriptionIds = new ArrayList<>();
        for (String iccid : event.iccids()) {
            Subscription sub = Subscription.builder()
                    .subscriptionId("SUB-" + UUID.randomUUID())
                    .orderId(event.orderId())
                    .iccid(iccid)
                    .customerId(order.customerId())
                    .accountNumber(event.accountNumber())
                    .ratePlanCode(event.ratePlanCode())
                    .status(SubscriptionStatus.ACTIVE)
                    .startDate(start)
                    .build();
            subscriptionRepository.save(sub);
            subscriptionIds.add(sub.getSubscriptionId());
        }

        eventPublisher.publishSubscriptionCreated(
                new SubscriptionCreatedEvent(event.orderId(), subscriptionIds, event.ratePlanCode(), start));
    }

    @Transactional
    public void terminateSubscriptions(SimDeactivatedEvent event) {
        List<Subscription> subs = subscriptionRepository.findByIccidIn(event.iccids());
        List<String> ids = new ArrayList<>();
        for (Subscription s : subs) {
            s.setStatus(SubscriptionStatus.TERMINATED);
            s.setEndDate(LocalDate.now());
            subscriptionRepository.save(s);
            ids.add(s.getSubscriptionId());
        }
        eventPublisher.publishSubscriptionTerminated(
                new SubscriptionTerminatedEvent(event.orderId(), ids, LocalDate.now()));
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getByOrder(String orderId) {
        return subscriptionRepository.findByOrderId(orderId).stream()
                .map(s -> new SubscriptionResponse(s.getSubscriptionId(), s.getOrderId(), s.getIccid(),
                        s.getRatePlanCode(), s.getStatus().name(), s.getStartDate()))
                .toList();
    }
}

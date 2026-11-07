package com.verizon.iot.billing.events;

import java.time.LocalDate;
import java.util.List;

/** Published to iot.billing.subscription-created. */
public record SubscriptionCreatedEvent(
        String orderId,
        List<String> subscriptionIds,
        String ratePlanCode,
        LocalDate startDate) {
}

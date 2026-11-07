package com.verizon.iot.billing.events;

import java.time.LocalDate;
import java.util.List;

/** Published to iot.billing.subscription-terminated (no consumer inside this repo - e.g. CRM/analytics). */
public record SubscriptionTerminatedEvent(String orderId, List<String> subscriptionIds, LocalDate endDate) {
}

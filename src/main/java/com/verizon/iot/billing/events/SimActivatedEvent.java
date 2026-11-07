package com.verizon.iot.billing.events;

import java.time.Instant;
import java.util.List;

/** Consumed from iot.sim.activated. */
public record SimActivatedEvent(
        String orderId,
        String reservationId,
        String customerId,
        String accountNumber,
        String ratePlanCode,
        List<String> iccids,
        Instant activatedAt) {
}

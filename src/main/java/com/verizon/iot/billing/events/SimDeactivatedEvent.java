package com.verizon.iot.billing.events;

import java.time.Instant;
import java.util.List;

/** Consumed from iot.sim.deactivated. */
public record SimDeactivatedEvent(String orderId, List<String> iccids, String reason, Instant deactivatedAt) {
}

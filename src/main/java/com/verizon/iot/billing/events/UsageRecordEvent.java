package com.verizon.iot.billing.events;

import java.time.Instant;

/** Consumed from iot.usage.records - produced by an EXTERNAL network-mediation system (no producer in this repo). */
public record UsageRecordEvent(String usageId, String iccid, long bytesUsed, Instant recordedAt) {
}

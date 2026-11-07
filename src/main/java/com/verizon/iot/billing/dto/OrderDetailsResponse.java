package com.verizon.iot.billing.dto;

import java.time.Instant;

/** Mirrors iot-order-service GET /api/v1/orders/{orderId}. */
public record OrderDetailsResponse(
        String orderId,
        String customerId,
        String simType,
        int quantity,
        String region,
        String ratePlanCode,
        String status,
        String failureReason,
        Instant createdAt) {
}

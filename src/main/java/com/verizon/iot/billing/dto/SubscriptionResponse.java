package com.verizon.iot.billing.dto;

import java.time.LocalDate;

public record SubscriptionResponse(String subscriptionId, String orderId, String iccid,
                                   String ratePlanCode, String status, LocalDate startDate) {
}

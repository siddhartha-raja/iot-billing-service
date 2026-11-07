package com.verizon.iot.billing.dto;

import java.math.BigDecimal;

public record RatePlanResponse(String code, String name, int dataAllowanceMb, BigDecimal monthlyFee, boolean active) {
}

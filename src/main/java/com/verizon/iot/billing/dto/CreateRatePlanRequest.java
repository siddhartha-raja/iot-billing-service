package com.verizon.iot.billing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateRatePlanRequest(
        @NotBlank String code,
        @NotBlank String name,
        @Positive int dataAllowanceMb,
        @Positive BigDecimal monthlyFee,
        BigDecimal overagePerMb) {
}

package com.verizon.iot.billing.exception;

public class RatePlanNotFoundException extends RuntimeException {
    public RatePlanNotFoundException(String code) {
        super("Rate plan not found: " + code);
    }
}

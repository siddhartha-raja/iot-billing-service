package com.verizon.iot.billing.controller;

import com.verizon.iot.billing.dto.CreateRatePlanRequest;
import com.verizon.iot.billing.dto.RatePlanResponse;
import com.verizon.iot.billing.dto.SubscriptionResponse;
import com.verizon.iot.billing.service.RatePlanService;
import com.verizon.iot.billing.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/billing")
@RequiredArgsConstructor
@Tag(name = "Billing")
public class BillingController {

    private final RatePlanService ratePlanService;
    private final SubscriptionService subscriptionService;

    /** Also called synchronously by iot-provisioning-service. */
    @GetMapping("/rate-plans/{code}")
    @Operation(summary = "Look up a rate plan")
    public RatePlanResponse getRatePlan(@PathVariable String code) {
        return ratePlanService.getByCode(code);
    }

    @GetMapping("/rate-plans")
    @Operation(summary = "List all rate plans")
    public List<RatePlanResponse> listRatePlans() {
        return ratePlanService.listAll();
    }

    @PostMapping("/rate-plans")
    @Operation(summary = "Create a rate plan (admin)")
    public RatePlanResponse createRatePlan(@Valid @RequestBody CreateRatePlanRequest request) {
        return ratePlanService.create(request);
    }

    @GetMapping("/subscriptions/{orderId}")
    @Operation(summary = "Subscriptions created for an order")
    public List<SubscriptionResponse> getSubscriptions(@PathVariable String orderId) {
        return subscriptionService.getByOrder(orderId);
    }
}

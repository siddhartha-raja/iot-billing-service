package com.verizon.iot.billing.client;

import com.verizon.iot.billing.dto.OrderDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Synchronous call back into iot-order-service to enrich billing data with customer info. */
@FeignClient(name = "iot-order-service", url = "${clients.order.base-url}")
public interface OrderClient {

    @GetMapping("/api/v1/orders/{orderId}")
    OrderDetailsResponse getOrder(@PathVariable("orderId") String orderId);
}

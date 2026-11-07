package com.verizon.iot.billing.config;

import com.verizon.iot.billing.entity.RatePlan;
import com.verizon.iot.billing.repository.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RatePlanSeeder implements CommandLineRunner {

    private final RatePlanRepository ratePlanRepository;

    @Override
    public void run(String... args) {
        ratePlanRepository.saveAll(List.of(
                RatePlan.builder().code("IOT-BASIC-1GB").name("IoT Basic 1GB").dataAllowanceMb(1024)
                        .monthlyFee(new BigDecimal("2.00")).overagePerMb(new BigDecimal("0.02")).active(true).build(),
                RatePlan.builder().code("IOT-STD-5GB").name("IoT Standard 5GB").dataAllowanceMb(5120)
                        .monthlyFee(new BigDecimal("6.50")).overagePerMb(new BigDecimal("0.01")).active(true).build(),
                RatePlan.builder().code("IOT-PREMIUM-20GB").name("IoT Premium 20GB").dataAllowanceMb(20480)
                        .monthlyFee(new BigDecimal("18.00")).overagePerMb(new BigDecimal("0.005")).active(true).build()));
    }
}

package com.verizon.iot.billing.service;

import com.verizon.iot.billing.entity.RatePlan;
import com.verizon.iot.billing.entity.Subscription;
import com.verizon.iot.billing.entity.SubscriptionStatus;
import com.verizon.iot.billing.events.InvoiceGeneratedEvent;
import com.verizon.iot.billing.messaging.BillingEventPublisher;
import com.verizon.iot.billing.repository.RatePlanRepository;
import com.verizon.iot.billing.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final SubscriptionRepository subscriptionRepository;
    private final RatePlanRepository ratePlanRepository;
    private final BillingEventPublisher eventPublisher;

    public void generateMonthlyInvoices(YearMonth period) {
        List<Subscription> active = subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE);
        Map<String, List<Subscription>> byAccount =
                active.stream().collect(Collectors.groupingBy(Subscription::getAccountNumber));

        byAccount.forEach((account, subs) -> {
            BigDecimal total = BigDecimal.ZERO;
            for (Subscription s : subs) {
                total = total.add(ratePlanRepository.findById(s.getRatePlanCode())
                        .map(RatePlan::getMonthlyFee).orElse(BigDecimal.ZERO));
            }
            eventPublisher.publishInvoiceGenerated(
                    new InvoiceGeneratedEvent(account, "INV-" + UUID.randomUUID(), period, total));
        });
        log.info("Generated invoices for {} accounts, period={}", byAccount.size(), period);
    }
}

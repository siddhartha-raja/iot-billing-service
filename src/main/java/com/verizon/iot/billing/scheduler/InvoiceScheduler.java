package com.verizon.iot.billing.scheduler;

import com.verizon.iot.billing.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

/** Time-triggered flow: no API or Kafka entry point. */
@Component
@RequiredArgsConstructor
public class InvoiceScheduler {

    private final InvoiceService invoiceService;

    @Scheduled(cron = "${billing.invoice.cron}")
    public void runMonthlyInvoicing() {
        invoiceService.generateMonthlyInvoices(YearMonth.now().minusMonths(1));
    }
}

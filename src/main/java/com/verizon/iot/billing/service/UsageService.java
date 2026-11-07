package com.verizon.iot.billing.service;

import com.verizon.iot.billing.entity.UsageRecord;
import com.verizon.iot.billing.events.UsageRecordEvent;
import com.verizon.iot.billing.repository.UsageRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageService {

    private final UsageRecordRepository usageRecordRepository;

    public void recordUsage(UsageRecordEvent event) {
        usageRecordRepository.save(UsageRecord.builder()
                .usageId(event.usageId())
                .iccid(event.iccid())
                .bytesUsed(event.bytesUsed())
                .recordedAt(event.recordedAt())
                .build());
    }
}

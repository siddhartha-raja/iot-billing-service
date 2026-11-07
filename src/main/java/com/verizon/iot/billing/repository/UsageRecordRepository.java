package com.verizon.iot.billing.repository;

import com.verizon.iot.billing.entity.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsageRecordRepository extends JpaRepository<UsageRecord, String> {
    List<UsageRecord> findByIccid(String iccid);
}

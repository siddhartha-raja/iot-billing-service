package com.verizon.iot.billing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "usage_records")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageRecord {

    @Id
    private String usageId;
    private String iccid;
    private long bytesUsed;
    private Instant recordedAt;
}

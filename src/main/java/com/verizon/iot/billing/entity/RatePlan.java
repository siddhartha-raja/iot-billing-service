package com.verizon.iot.billing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "rate_plans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatePlan {

    @Id
    private String code;
    private String name;
    private int dataAllowanceMb;
    private BigDecimal monthlyFee;
    private BigDecimal overagePerMb;
    private boolean active;
}

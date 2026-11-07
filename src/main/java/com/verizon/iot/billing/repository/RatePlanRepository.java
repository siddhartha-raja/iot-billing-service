package com.verizon.iot.billing.repository;

import com.verizon.iot.billing.entity.RatePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatePlanRepository extends JpaRepository<RatePlan, String> {
}

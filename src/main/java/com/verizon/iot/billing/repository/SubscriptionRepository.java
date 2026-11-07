package com.verizon.iot.billing.repository;

import com.verizon.iot.billing.entity.Subscription;
import com.verizon.iot.billing.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    List<Subscription> findByOrderId(String orderId);
    List<Subscription> findByIccidIn(List<String> iccids);
    List<Subscription> findByStatus(SubscriptionStatus status);
}

package com.verizon.iot.billing.service;

import com.verizon.iot.billing.dto.CreateRatePlanRequest;
import com.verizon.iot.billing.dto.RatePlanResponse;
import com.verizon.iot.billing.entity.RatePlan;
import com.verizon.iot.billing.exception.RatePlanNotFoundException;
import com.verizon.iot.billing.repository.RatePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatePlanService {

    private final RatePlanRepository ratePlanRepository;

    public RatePlanResponse getByCode(String code) {
        RatePlan p = ratePlanRepository.findById(code).orElseThrow(() -> new RatePlanNotFoundException(code));
        return toResponse(p);
    }

    public List<RatePlanResponse> listAll() {
        return ratePlanRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RatePlanResponse create(CreateRatePlanRequest req) {
        RatePlan saved = ratePlanRepository.save(RatePlan.builder()
                .code(req.code())
                .name(req.name())
                .dataAllowanceMb(req.dataAllowanceMb())
                .monthlyFee(req.monthlyFee())
                .overagePerMb(req.overagePerMb() == null ? BigDecimal.ZERO : req.overagePerMb())
                .active(true)
                .build());
        return toResponse(saved);
    }

    private RatePlanResponse toResponse(RatePlan p) {
        return new RatePlanResponse(p.getCode(), p.getName(), p.getDataAllowanceMb(), p.getMonthlyFee(), p.isActive());
    }
}

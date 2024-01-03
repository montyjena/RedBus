package com.redBus.operator.repository;

import com.redBus.operator.entity.TicketCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCostRepository extends JpaRepository<TicketCost, String> {
}

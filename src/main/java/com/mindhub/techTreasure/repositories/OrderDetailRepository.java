package com.mindhub.techTreasure.repositories;

import com.mindhub.techTreasure.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}

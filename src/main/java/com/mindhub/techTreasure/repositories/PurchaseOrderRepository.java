package com.mindhub.techTreasure.repositories;

import com.mindhub.techTreasure.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
   boolean existsByNumber(String number);
//   void save(PurchaseOrder purchaseOrder);
}

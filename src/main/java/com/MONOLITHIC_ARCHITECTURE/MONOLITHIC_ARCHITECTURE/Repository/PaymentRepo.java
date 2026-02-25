package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByOrder_User_Id(Long userId);  // For customer to see their own payments
}

package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser_Username(String username);

}

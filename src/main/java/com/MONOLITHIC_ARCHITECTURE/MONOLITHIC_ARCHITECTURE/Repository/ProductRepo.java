package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCategory_Id(Long categoryId);
}


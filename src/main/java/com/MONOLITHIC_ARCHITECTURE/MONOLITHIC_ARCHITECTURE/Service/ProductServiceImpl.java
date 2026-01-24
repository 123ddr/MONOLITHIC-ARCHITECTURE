package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config.SecurityUtils;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductUpdateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.CategoryEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.ProductEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.CategoryRepo;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final SecurityUtils securityUtils;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo,
                              CategoryRepo categoryRepo,
                              SecurityUtils securityUtils) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.securityUtils = securityUtils;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {

        CategoryEntity category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("CATEGORY NOT FOUND"));

        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        productRepo.save(product);

        return toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {

        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND"));

        if (request.getName() != null && !request.getName().isBlank()) {
            product.setName(request.getName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }

        if (request.getCategoryId() != null) {
            CategoryEntity category = categoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("CATEGORY NOT FOUND"));
            product.setCategory(category);
        }

        productRepo.save(product);
        return toResponse(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {

        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND"));

        productRepo.delete(product);
    }

    @Override
    public ProductResponse getProductById(Long productId) {

        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("PRODUCT NOT FOUND"));

        return toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ===================== MAPPER ===================== */

    private ProductResponse toResponse(ProductEntity entity) {
        ProductResponse dto = new ProductResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setCategoryName(entity.getCategory().getName());
        return dto;
    }
}


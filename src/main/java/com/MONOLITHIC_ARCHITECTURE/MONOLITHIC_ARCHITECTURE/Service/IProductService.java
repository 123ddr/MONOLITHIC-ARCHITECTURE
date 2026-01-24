package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    ProductResponse createProduct(ProductCreateRequest request);          // ADMIN

    ProductResponse updateProduct(Long productId, ProductUpdateRequest request); // ADMIN

    void deleteProduct(Long productId);                                   // ADMIN

    ProductResponse getProductById(Long productId);                       // ADMIN

    List<ProductResponse> getAllProducts();                               // ADMIN + CUSTOMER
}

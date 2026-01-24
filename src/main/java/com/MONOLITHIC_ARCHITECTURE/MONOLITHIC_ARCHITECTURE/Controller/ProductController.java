package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Controller;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.ProductUpdateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response.Response;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * Create product (ADMIN)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> create(@RequestBody ProductCreateRequest request) {
        try {
            ProductResponse product = productService.createProduct(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Response.success(HttpStatus.CREATED, product));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT_CREATION_FAILED : " + e.getMessage()));
        }
    }

    /**
     * Update product (ADMIN)
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> update(@PathVariable Long id,
                                           @RequestBody ProductUpdateRequest request) {
        try {
            ProductResponse product = productService.updateProduct(id, request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Response.success(HttpStatus.OK, product));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT_UPDATE_FAILED : " + e.getMessage()));
        }
    }

    /**
     * Delete product (ADMIN)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(Response.success(HttpStatus.NO_CONTENT, "PRODUCT_DELETED_SUCCESSFULLY"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT_DELETE_FAILED : " + e.getMessage()));
        }
    }

    /**
     * Get product by id (ADMIN)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Response.success(HttpStatus.OK, product));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Response.error(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND : " + e.getMessage()));
        }
    }

    /**
     * Get all products (ADMIN + CUSTOMER)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public ResponseEntity<Response> getAll() {
        try {
            List<ProductResponse> products = productService.getAllProducts();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Response.success(HttpStatus.OK, products));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT_LIST_FETCH_FAILED : " + e.getMessage()));
        }
    }
}


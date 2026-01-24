package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Controller;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryUpdateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service.ICategoryService;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final ICategoryService categoryService;

    /**
     * Constructs CategoryController with category service
     *
     * @param categoryService the category service
     */
    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /* ======================= ADMIN ENDPOINTS ======================= */

    /**
     * Create category (ADMIN)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> createCategory(
            @RequestBody CategoryCreateRequest request) {
        try {
            CategoryResponse category = categoryService.createCategory(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Response.success(HttpStatus.CREATED, category));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    /**
     * Update category (ADMIN)
     */
    @PatchMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody CategoryUpdateRequest request) {
        try {
            CategoryResponse category =
                    categoryService.updateCategory(categoryId, request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Response.success(HttpStatus.OK, category));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    /**
     * Delete category (ADMIN)
     */
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteCategory(
            @PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(Response.success(
                            HttpStatus.NO_CONTENT,
                            "Category deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    /**
     * Get category by ID (ADMIN)
     */
    @GetMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getCategoryById(
            @PathVariable Long categoryId) {
        try {
            CategoryResponse category =
                    categoryService.getCategoryById(categoryId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Response.success(HttpStatus.OK, category));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

    /* ======================= COMMON ENDPOINTS ======================= */

    /**
     * Get all categories (ADMIN + USER)
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Response> getAllCategories() {
        try {
            List<CategoryResponse> categories =
                    categoryService.getAllCategories();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Response.success(HttpStatus.OK, categories));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}


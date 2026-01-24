package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryUpdateRequest;

import java.util.List;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryCreateRequest request);   // ADMIN

    CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request); // ADMIN

    void deleteCategory(Long categoryId); // ADMIN

    CategoryResponse getCategoryById(Long categoryId); // ADMIN

    List<CategoryResponse> getAllCategories(); // ADMIN + CUSTOMER
}

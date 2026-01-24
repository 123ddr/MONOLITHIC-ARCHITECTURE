package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config.SecurityUtils;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.CategoryUpdateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.CategoryEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.CategoryRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepo categoryRepo;
    private final SecurityUtils securityUtils;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo,
                               SecurityUtils securityUtils) {
        this.categoryRepo = categoryRepo;
        this.securityUtils = securityUtils;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {

        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());

        categoryRepo.save(category);

        return toResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest request) {

        CategoryEntity category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("CATEGORY NOT FOUND"));

        if (request.getName() != null && !request.getName().isBlank()) {
            category.setName(request.getName());
        }

        categoryRepo.save(category);
        return toResponse(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {

        CategoryEntity category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("CATEGORY NOT FOUND"));

        categoryRepo.delete(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {

        CategoryEntity category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("CATEGORY NOT FOUND"));

        return toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ===================== MAPPER ===================== */

    private CategoryResponse toResponse(CategoryEntity entity) {
        CategoryResponse dto = new CategoryResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}


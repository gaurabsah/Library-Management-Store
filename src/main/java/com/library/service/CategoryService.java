package com.library.service;

import com.library.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDto, int categoryId);

    void deleteCategory(int categoryId);

    CategoryDTO getCategory(int categoryId);

    List<CategoryDTO> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);
}

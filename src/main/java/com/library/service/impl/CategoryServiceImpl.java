package com.library.service.impl;

import com.library.dao.CategoryRepository;
import com.library.dto.CategoryDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Category;
import com.library.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = mapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = mapper.map(savedCategory, CategoryDTO.class);
        logger.info("Created category: {}", savedCategoryDTO.categoryId);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setCategoryName(categoryDto.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO categoryDTO = mapper.map(savedCategory, CategoryDTO.class);
        logger.info("Updated category: {}", categoryDTO.categoryId);
        return categoryDTO;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
        logger.info("Deleted category: {}", categoryId);
    }

    @Override
    public CategoryDTO getCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);
        logger.info("Found category: {}", categoryDTO.categoryId);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        List<Category> categories = page.getContent();
        List<CategoryDTO> categoryDTOList = categories.stream().map(category -> mapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        logger.info("Categories found: {}", categoryDTOList.size());
        return categoryDTOList;
    }
}

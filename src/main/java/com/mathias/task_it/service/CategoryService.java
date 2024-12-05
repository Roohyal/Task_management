package com.mathias.task_it.service;

import com.mathias.task_it.payload.request.CategoryDto;
import com.mathias.task_it.payload.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCurrentUserCategories(String email);

    CategoryResponse createCategory(CategoryDto categoryDto, String email);
}

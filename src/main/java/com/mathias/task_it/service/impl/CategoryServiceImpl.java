package com.mathias.task_it.service.impl;

import com.mathias.task_it.domain.entities.Categories;
import com.mathias.task_it.exceptions.NotFoundException;
import com.mathias.task_it.payload.request.CategoryDto;
import com.mathias.task_it.payload.response.CategoryResponse;
import com.mathias.task_it.repository.CategoryRepository;
import com.mathias.task_it.repository.PersonRepository;
import com.mathias.task_it.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;


    @Override
    public List<CategoryDto> getAllCurrentUserCategories(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));
        return categoryRepository.findAllByPersonEmail(email)
                .stream()
                .map(category -> new CategoryDto( category.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse createCategory(CategoryDto categoryDto, String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        Categories category = new Categories();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);

        return CategoryResponse.builder()
                .responseCode("006")
                .responseMessage("Your category has been created successfully")
                .build();
    }
}

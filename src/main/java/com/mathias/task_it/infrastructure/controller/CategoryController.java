package com.mathias.task_it.infrastructure.controller;

import com.mathias.task_it.payload.request.CategoryDto;
import com.mathias.task_it.payload.response.CategoryResponse;
import com.mathias.task_it.repository.CategoryRepository;
import com.mathias.task_it.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryDto categoryDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        CategoryResponse categoryResponse = categoryService.createCategory(categoryDto, currentUsername);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return ResponseEntity.ok(categoryService.getAllCurrentUserCategories(currentUsername));
    }
}

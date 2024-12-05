package com.mathias.task_it.repository;

import com.mathias.task_it.domain.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories, Long> {

    List<Categories> findAllByPersonEmail(String email);
}

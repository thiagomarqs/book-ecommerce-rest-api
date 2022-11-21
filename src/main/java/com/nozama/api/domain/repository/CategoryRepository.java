package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByName(String name);

}
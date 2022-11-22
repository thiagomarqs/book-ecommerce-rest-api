package com.nozama.api.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nozama.api.domain.entity.Category;
import com.nozama.api.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Override
  @Query(value = "SELECT c FROM Category c WHERE c.active = true and c.id = :id")
  Optional<Category> findById(@Param("id") Long id);
  
  @Override
  @Query(value = "SELECT c FROM Category c WHERE c.active = true")
  List<Category> findAll();

  Boolean existsByName(String name);

}
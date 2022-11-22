package com.nozama.api.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nozama.api.domain.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

  @Override
  @Query(value = "SELECT a FROM Author a WHERE a.active = true and a.id = :id")
  Optional<Author> findById(@Param("id") Long id);
  
  @Override
  @Query(value = "SELECT a FROM Author a WHERE a.active = true")
  List<Author> findAll();

  Boolean existsByName(String name);
}

package com.nozama.api.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nozama.api.domain.entity.Publisher;
import com.nozama.api.domain.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

  @Override
  @Query(value = "SELECT p FROM Publisher p WHERE p.active = true and p.id = :id")
  Optional<Publisher> findById(@Param("id") Long id);
  
  @Override
  @Query(value = "SELECT p FROM Publisher p WHERE p.active = true")
  List<Publisher> findAll();

  Boolean existsByName(String name);
}
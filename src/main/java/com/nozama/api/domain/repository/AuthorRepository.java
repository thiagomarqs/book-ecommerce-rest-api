package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}

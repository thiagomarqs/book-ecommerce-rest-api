package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Boolean existsByName(String name);

}
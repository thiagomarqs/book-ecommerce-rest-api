package com.nozama.api.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nozama.api.domain.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  Boolean existsBySku(String sku);

  Boolean existsByIsbn(String isbn);

  @Modifying
  @Query(value = "UPDATE Book b SET b.active = false WHERE b.publisher.id = :id")
  @Transactional
  int inactivateByPublisherId(@Param("id") Long id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Book b SET b.active = true WHERE b.publisher.id = :id")
  int activateByPublisherId(@Param("id") Long id);
  
}
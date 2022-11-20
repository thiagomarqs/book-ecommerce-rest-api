package com.nozama.api.domain.usecase.book;

import com.nozama.api.application.controller.CategoryController;
import com.nozama.api.application.dto.request.book.BookCreateRequest;
import com.nozama.api.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Manage Book Use Case")
@SpringBootTest
class ManageBookTest {

    @Autowired
    private CategoryController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
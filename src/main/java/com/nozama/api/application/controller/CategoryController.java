package com.nozama.api.application.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.application.dto.request.category.CategoryRequest;
import com.nozama.api.application.dto.response.category.CategoryResponse;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Category;
import com.nozama.api.domain.usecase.category.ManageCategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Operations for managing categories.")
public class CategoryController {

  @Autowired
  private ManageCategory manageCategoryUseCase;

  @Autowired
  private EntityMapper entityMapper;

  private Link[] links = {
    Link.of("/api/categories"),
    Link.of("/api/categories/{id}", "category")
  };

  @PostMapping(
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Creates an category", 
    description = "Creates an category based on the request's body payload.", 
    tags = { "Admin", "Category" }
  )
  public ResponseEntity<CategoryResponse> create(@RequestBody @Parameter(description = "The new category") CategoryRequest payload) {
    Category category = entityMapper.mapEntity(payload, Category.class);

    category = manageCategoryUseCase.create(category);

    CategoryResponse response = entityMapper.mapEntity(category, CategoryResponse.class).setLinks();

    URI uri = response.getRequiredLink(IanaLinkRelations.SELF).toUri();

    return ResponseEntity.created(uri).body(response);
  }

  @GetMapping(
    value = "/{id}", 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Finds an category", 
    description = "Finds an category by its Id.", 
    tags = { "Category" }
  )
  public ResponseEntity<CategoryResponse> findById(@PathVariable(value = "id") @Parameter(description = "The id of the category.") Long id) {
    Category found = manageCategoryUseCase.findById(id);
    CategoryResponse response = entityMapper.mapEntity(found, CategoryResponse.class).setLinks();

    return ResponseEntity.ok(response);
  }

  @GetMapping(
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Finds all categories", 
    description = "Finds all categories. If no category exists, an empty array will be returned.", 
    tags = { "Category" }
  )
  public ResponseEntity<CollectionModel<CategoryResponse>> findAll() {
    List<Category> found = manageCategoryUseCase.findAll();
    List<CategoryResponse> list = entityMapper.mapList(found, CategoryResponse.class);
    var response = CollectionModel.of(list, links);

    return ResponseEntity.ok(response);
  }

  @PutMapping(
    value = "/{id}", 
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Updates an category by its id", 
    description = "Finds an category by the provided id and updates it. If the provided id is invalid, an exception will be thrown.", 
    tags = { "Admin", "Category" }
  )
  public ResponseEntity<CategoryResponse> update(@PathVariable(value = "id") @Parameter(description = "The id of the category.") Long id, @RequestBody @Parameter(description = "The new information of the category.") CategoryRequest payload) {
    Category category = entityMapper.mapEntity(payload, Category.class);
    category.setId(id);

    category = manageCategoryUseCase.update(category);

    CategoryResponse response = entityMapper.mapEntity(category, CategoryResponse.class).setLinks();

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @Operation(
    summary = "Deletes an category by its id", 
    description = "Finds an category by the provided id and deletes it. If the provided id is invalid, an exception will be thrown.", 
    tags = { "Admin", "Category" }
  )
  public ResponseEntity<?> delete(@PathVariable(value = "id") @Parameter(description = "The id of the category") Long id) {
    manageCategoryUseCase.delete(id);
    return ResponseEntity.noContent().build();
  }
}

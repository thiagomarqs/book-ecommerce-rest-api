package com.nozama.api.domain.usecase.category;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Category;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.InvalidEntityException;
import com.nozama.api.domain.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class ManageCategoryTest {

  @Mock
  private CategoryRepository repository;

  @InjectMocks
  private ManageCategory manageCategory;

  @Test
  void Given_NullCategory_When_CreatingCategory_Then_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> manageCategory.create(null));
  }

  @Test
  void Given_AlreadyExistingCategoryName_When_CreatingCategory_Then_ThrowException() {
    
    Category category = new Category(1L, "Test", "Test", null);
    
    when(repository.existsByName("Test")).thenReturn(true);

    assertThrows(InvalidEntityException.class, () -> manageCategory.create(category));

  }

  @Test
  void Given_NullCategoryId_When_FindingCategory_Then_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> manageCategory.findById(null));
  }

  @Test
  void Given_InexistingCategoryId_When_FindingById_Then_ThrowException() {

    when(repository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(InvalidEntityException.class, () -> manageCategory.findById(1L));

  }

  @Test
  void Given_NullCategoryId_When_UpdatingCategory_Then_ThrowException() {
    assertThrows(InvalidEntityException.class, () -> manageCategory.update(null));
  }

  @Test
  void Given_InexistingCategoryId_When_UpdatingCategory_Then_ThrowException() {

    Category category = new Category(1L, "Test", "Test", true);
    
    when(repository.existsById(1L)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> manageCategory.update(category));

  }

  @Test
  void Given_NullCategoryId_When_DeletingCategory_Then_ThrowException() {
    assertThrows(IllegalArgumentException.class, () -> manageCategory.delete(null));
  }

  @Test
  void Given_InexistingCategoryId_When_DeletingCategory_Then_ThrowException() {

    when(repository.existsById(1L)).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> manageCategory.delete(1L));

  }

}

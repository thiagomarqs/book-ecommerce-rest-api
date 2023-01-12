package com.nozama.api.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CartItemRepositoryTest {

  @Autowired
  private CartItemRepository repository;
  
  
  /** getCartTotal() */

  @Test
  void Given_CustomerIdEquals2_When_GettingCartTotal_Expect_TotalEquals75_98() {
    var total = repository.getCartTotal(2L);
    assertEquals(BigDecimal.valueOf(75.98), total);
  }

  @Test
  void Given_InexistingCustomer_When_GettingCartTotal_Expect_Null() {
    var total = repository.getCartTotal(23812893712L);
    assertEquals(null, total);
  }



  /** getByCustomerId() */

  @Test
  void Given_CustomerIdEquals2_When_GettingCartByCustomer_Expect_CustomerCartItems() {
    var cart = repository.getByCustomerId(2L);
    assertTrue(() -> cart.size() > 0);
  }

  @Test
  void Given_CustomerIdEquals2_When_GettingCartByCustomer_Expect_AllItemsToBelongToCustomer2() {
    var cart = repository.getByCustomerId(2L);
    assertTrue(() -> cart.stream().allMatch((i) -> i.getCustomer().getId() == 2L));
  }



  /** updateCartItemUnits() */

  @Test
  void Given_CurrentUnitsIs1_When_UpdatingCartItemUnits_Expect_NewUnitsToBe5() {
    var cartItemToTestId = 1L;
    var cartItemBeforeUpdate = repository.findById(cartItemToTestId).get();
    
    assertTrue(cartItemBeforeUpdate.getUnits() == 1);

    var rowsModified = repository.updateCartItemUnits(cartItemToTestId, 5);

    assertEquals(1, rowsModified);

    var cartItemAfterUpdate = repository.findById(cartItemToTestId).get();

    assertEquals(5, cartItemAfterUpdate.getUnits());
  }

}

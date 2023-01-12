package com.nozama.api.domain.usecase.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.entity.CartItem;
import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.enums.Currency;
import com.nozama.api.domain.exception.OperationNotAllowedException;
import com.nozama.api.domain.repository.CartItemRepository;
import com.nozama.api.domain.repository.CustomerRepository;
import com.nozama.api.domain.usecase.customer.ManageCustomer;
import com.nozama.api.domain.vo.Price;

@ExtendWith(MockitoExtension.class)
public class ManageCartTest {
  
  @Mock
  private CustomerRepository customerRepository;
  
  @Mock
  private CartItemRepository cartItemRepository;

  @Mock
  private ManageCustomer manageCustomer;

  @InjectMocks
  private ManageCart manageCart;

  @Test
  void Given_NoItemsInCustomerCart_When_AddingOneItem_Expect_OneItem() {
    final var customerId = 1L;
    final var item = new CartItem();
    
    var customer = new Customer();
    when(manageCustomer.findById(anyLong())).thenReturn(customer);

    assertEquals(0, customer.getCartItems().size());

    List<CartItem> customerCart = manageCart.addItem(customerId, item);

    verify(customerRepository).save(any());

    assertEquals(1, customerCart.size());
  }

  @Test 
  void Given_BookAlreadyInCart_When_AddingItem_Expect_Exception() {
    final var customerId = 1L;
    final var existingItem = new CartItem();
    final var itemToAdd = new CartItem();
    final var book = new Book();
    var customer = new Customer();
    
    when(manageCustomer.findById(anyLong())).thenReturn(customer);

    book.setId(1L);
    book.setPrice(new Price(new BigDecimal(29.99), Currency.BRL));

    existingItem.setBook(book);
    existingItem.setUnits(1);
    existingItem.setInitialInfo();
    
    customer.addCartItems(List.of(existingItem));

    itemToAdd.setBook(book);
    itemToAdd.setUnits(2);

    when(manageCustomer.findById(anyLong())).thenReturn(customer);

    assertThrows(
      OperationNotAllowedException.class, 
      () -> manageCart.addItem(customerId, itemToAdd)
    );
  }


  @Test
  void Given_ValidId_When_RemovingItem_Expect_ItemRemovedSuccessfully() {
    final var cartItemId = 2L;
    when(cartItemRepository.existsById(anyLong())).thenReturn(true);
    manageCart.deleteItem(1L, cartItemId);
    verify(cartItemRepository).deleteById(any());
  }

  @Test
  void When_UpdatingUnits_Expect_CallUpdateUnits() {
    when(cartItemRepository.existsById(anyLong())).thenReturn(true);
    when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(mock(CartItem.class)));
    manageCart.updateCartItemUnits(1L, 1L, 5);
    verify(cartItemRepository).updateCartItemUnits(1L, 5);
  }

  @Test
  void When_GettingCustomerCart_Expect_CustomerCartItems() {
   
    final var customerId = 1L;
    var item1 = new CartItem();
    item1.setPrice(new BigDecimal(200.00));
    
    var item2 = new CartItem();
    item2.setPrice(new BigDecimal(50.00));

    final var mock = List.of(item1, item2);

    var customer = new Customer();
    customer.addCartItems(mock);
    
    doReturn(mock).when(cartItemRepository).getByCustomerId(customerId);

    final var cart = manageCart.getCart(customerId);

    assertEquals(2, cart.size());
  }

  @Test
  void When_GettingOneItem_Expect_Item() {
    when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(new CartItem()));
    when(cartItemRepository.existsById(anyLong())).thenReturn(true);
    when(cartItemRepository.itemBelongsToCustomer(anyLong(), anyLong())).thenReturn(true);

    var item = manageCart.getItem(1L, 1L);
    
    assertNotNull(item);
  }
}

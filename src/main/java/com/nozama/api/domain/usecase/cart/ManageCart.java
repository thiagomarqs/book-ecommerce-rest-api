package com.nozama.api.domain.usecase.cart;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.CartItem;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.OperationNotAllowedException;
import com.nozama.api.domain.repository.CartItemRepository;
import com.nozama.api.domain.repository.CustomerRepository;
import com.nozama.api.domain.usecase.customer.ManageCustomer;

@Component
public class ManageCart {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private ManageCustomer manageCustomerUseCase;

  public List<CartItem> getCart(Long customerId) {
    return cartItemRepository.getByCustomerId(customerId);
  }

  public CartItem getItem(Long customerId, Long itemId) {
    throwIfInvalidItem(customerId, itemId);
    return cartItemRepository.findById(itemId).get();
  }

  public List<CartItem> addItem(Long customerId, CartItem itemToAdd) {
    var customer = manageCustomerUseCase.find(customerId);
    var cart = customer.getCartItems();

    Predicate<CartItem> existingItemWithTheSameBookAsItemToAdd = existing -> existing.getBook().getId().equals(itemToAdd.getBook().getId());
    
    var isBookAlreadyAdded = cart.stream().anyMatch(existingItemWithTheSameBookAsItemToAdd);

    if(isBookAlreadyAdded) {
      throw new OperationNotAllowedException("This book was already added to the cart. To add another unit, update the item.");
    }

    itemToAdd.setInitialInfo();

    customer.addCartItem(itemToAdd);

    customerRepository.save(customer);

    return customer.getCartItems();
  }

  public void deleteItem(Long customerId, Long cartItemId) {
    throwIfInvalidItem(customerId, cartItemId);
    cartItemRepository.deleteById(cartItemId);
  }

  public CartItem updateCartItemUnits(Long customerId, Long cartItemId, Integer newUnit) {
    throwIfInvalidItem(customerId, cartItemId);
    cartItemRepository.updateCartItemUnits(cartItemId, newUnit);
    return cartItemRepository.findById(cartItemId).get();
  }
 
  private void throwIfInvalidItem(Long customerId, Long cartItemId) {
    if(cartItemId == null) throw new IllegalArgumentException("No id was informed.");
    if(!cartItemRepository.existsById(cartItemId)) throw new EntityNotFoundException("Item with id " + cartItemId + " was not found.");
    if(!cartItemRepository.itemBelongsToCustomer(cartItemId, customerId)) throw new OperationNotAllowedException("This item does not exist in this cart.");
  }

  public void emptyCart(Long customerId) {
    cartItemRepository.emptyCart(customerId);
  }
}

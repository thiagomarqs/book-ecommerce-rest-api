package com.nozama.api.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nozama.api.domain.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE CartItem c SET c.units = :units, c.subTotal = c.price * :units WHERE c.id = :cartItemId")
  int updateCartItemUnits(Long cartItemId, Integer units);

  @Query("SELECT c FROM CartItem c WHERE c.customer.id = :customerId")
  List<CartItem> getByCustomerId(Long customerId);
  
  @Query("SELECT SUM(c.subTotal) FROM CartItem c WHERE c.customer.id = :customerId")
  BigDecimal getCartTotal(Long customerId);

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CartItem c WHERE c.id = :cartItemId AND c.customer.id = :customerId")
  boolean itemBelongsToCustomer(Long cartItemId, Long customerId);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("DELETE CartItem c WHERE c.customer.id = :customerId")
  int emptyCart(Long customerId);

}

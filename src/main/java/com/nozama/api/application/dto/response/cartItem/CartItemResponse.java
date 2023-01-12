package com.nozama.api.application.dto.response.cartItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import com.nozama.api.application.dto.response.book.BookResponse;
import com.nozama.api.domain.enums.Currency;

public class CartItemResponse extends EntityModel<CartItemResponse> {

  private Long id;
  private BookResponse book;
  private BigDecimal price;
  private Integer units;
  private BigDecimal subTotal;
  private Currency currency;
  private LocalDateTime addedAt;
  private LocalDateTime modifiedAt;

  public CartItemResponse setLinks() {

    Link self = Link.of("/{customerId}/cart/items/{itemId}");
    Link all = Link.of("/{customerId}/cart/");
    Link[] links = { self, all};

    this.add(links);

    return this;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BookResponse getBook() {
    return book;
  }

  public void setBook(BookResponse book) {
    this.book = book;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getUnits() {
    return units;
  }

  public void setUnits(Integer units) {
    this.units = units;
  }

  public BigDecimal getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public LocalDateTime getAddedAt() {
    return addedAt;
  }

  public void setAddedAt(LocalDateTime addedAt) {
    this.addedAt = addedAt;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

}

package com.nozama.api.application.dto.response.cartItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.Link;

import com.nozama.api.domain.enums.Currency;

public class CartItemResponse extends RepresentationModel<CartItemResponse> {

  private Long id;
  private Long bookId;
  private BigDecimal price;
  private Integer units;
  private BigDecimal subTotal;
  private Currency currency;
  private LocalDateTime addedAt;
  private LocalDateTime modifiedAt;

  public CartItemResponse setLinks() {

    Link self = Link.of("/{customerId}/cart/items/{itemId}");
    Link all = Link.of("/{customerId}/cart/");
    Link book = Link.of("/books/" + bookId);
    Link[] links = { self, all, book };

    this.add(links);

    return this;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

}

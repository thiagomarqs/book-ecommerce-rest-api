package com.nozama.api.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nozama.api.domain.enums.Currency;
import com.nozama.api.domain.vo.Price;

@Entity
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id", updatable = false)
  @JsonIgnore
  private Customer customer;

  @OneToOne
  @JoinColumn(name = "book_id", updatable = false)
  private Book book;

  @NotNull
  private BigDecimal price;

  @NotNull
  @Min(value = 1, message = "A cart item must contain at least one unit of its product.")
  private Integer units;

  @NotNull
  private BigDecimal subTotal;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Currency currency;

  @NotNull
  private LocalDateTime addedAt;

  @NotNull
  private LocalDateTime modifiedAt;

  public CartItem(Customer customer, Book book, Price bookPrice, Integer units) {
    this.customer = customer;
    this.book = book;
    this.price = bookPrice.getPrice();
    this.currency = bookPrice.getCurrency();
    this.subTotal = calculateSubTotal(bookPrice, units);
    this.units = units;
    this.addedAt = LocalDateTime.now();
    this.modifiedAt = LocalDateTime.now();
  }

  public CartItem() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
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

  public BigDecimal getSubTotal() {
    return subTotal;
  }

  public void setInitialInfo() {
    calculateAndSetSubtotal();
    setAddedAt(LocalDateTime.now());
  }

  public void calculateAndSetSubtotal() {
    this.subTotal = calculateSubTotal(book.getPrice(), units);
    setModifiedAt(LocalDateTime.now());
  }

  private BigDecimal calculateSubTotal(Price price, Integer units) {
    return price.getPrice().multiply(new BigDecimal(units));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CartItem other = (CartItem) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}

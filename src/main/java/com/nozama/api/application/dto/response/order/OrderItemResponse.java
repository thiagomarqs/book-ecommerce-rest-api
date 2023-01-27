package com.nozama.api.application.dto.response.order;

import java.math.BigDecimal;

public class OrderItemResponse {
  
  private Long id;
  private Long bookId;
  private BigDecimal price;
  private int units;
  private BigDecimal subTotal;

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
  public int getUnits() {
    return units;
  }
  public void setUnits(int units) {
    this.units = units;
  }
  public BigDecimal getSubTotal() {
    return subTotal;
  }
  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }
  public Long getBookId() {
    return bookId;
  }
  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

}

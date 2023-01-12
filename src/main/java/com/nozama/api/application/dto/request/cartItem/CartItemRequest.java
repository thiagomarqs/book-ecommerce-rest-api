package com.nozama.api.application.dto.request.cartItem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartItemRequest {
  
  @NotNull
  private Long bookId;

  @NotNull
  @Positive
  private Integer units;

  public Long getBookId() {
    return bookId;
  }
  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }
  public Integer getUnits() {
    return units;
  }
  public void setUnits(Integer units) {
    this.units = units;
  }

}

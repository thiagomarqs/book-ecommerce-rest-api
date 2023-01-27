package com.nozama.api.application.dto.request.cartItem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UpdateCartItemUnitsRequest {
  
  @NotNull
  @Positive
  private Integer units;

  public Integer getUnits() {
    return units;
  }

  public void setUnits(Integer newUnits) {
    this.units = newUnits;
  }

}

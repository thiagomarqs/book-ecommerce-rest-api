package com.nozama.api.application.dto.request.order;

import java.util.UUID;

public class OrderRequest {

  private UUID paymentMethodId;
  private Long addressId;
  
  public UUID getPaymentMethodId() {
    return paymentMethodId;
  }
  public void setPaymentMethodId(UUID paymentMethodId) {
    this.paymentMethodId = paymentMethodId;
  }
  public Long getAddressId() {
    return addressId;
  }
  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }
}

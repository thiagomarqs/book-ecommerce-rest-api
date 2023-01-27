package com.nozama.api.application.dto.response.paymentMethod;

import java.util.UUID;

import com.nozama.api.domain.enums.PaymentMethodType;

public class PaymentMethodResponse {

  private UUID id;
  private PaymentMethodType type;
  private String number;
  private String holder;
  private String expMonth;
  private String expYear;
  
  public UUID getId() {
    return id;
  }
  public void setId(UUID id) {
    this.id = id;
  }
  public PaymentMethodType getType() {
    return type;
  }
  public void setType(PaymentMethodType type) {
    this.type = type;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String number) {
    this.number = number;
  }
  public String getHolder() {
    return holder;
  }
  public void setHolder(String holder) {
    this.holder = holder;
  }
  public String getExpMonth() {
    return expMonth;
  }
  public void setExpMonth(String expirationMonth) {
    this.expMonth = expirationMonth;
  }
  public String getExpYear() {
    return expYear;
  }
  public void setExpYear(String expirationYear) {
    this.expYear = expirationYear;
  }

  
}

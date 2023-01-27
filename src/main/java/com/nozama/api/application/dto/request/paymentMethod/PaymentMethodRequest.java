package com.nozama.api.application.dto.request.paymentMethod;

import com.nozama.api.domain.enums.PaymentMethodType;

public class PaymentMethodRequest {
  private PaymentMethodType type;
  private String number;
  private String holder;
  private String expirationMonth;
  private String expirationYear;
  private String cvv;

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
  public String getExpirationMonth() {
    return expirationMonth;
  }
  public void setExpirationMonth(String expirationMonth) {
    this.expirationMonth = expirationMonth;
  }
  public String getExpirationYear() {
    return expirationYear;
  }
  public void setExpirationYear(String expirationYear) {
    this.expirationYear = expirationYear;
  }
  public String getCvv() {
    return cvv;
  }
  public void setCvv(String cvv) {
    this.cvv = cvv;
  }
  
}

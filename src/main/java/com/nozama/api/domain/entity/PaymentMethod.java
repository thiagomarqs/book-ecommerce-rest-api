package com.nozama.api.domain.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.nozama.api.domain.enums.PaymentMethodType;

// Once again, in a real scenario, this obviously wouldn't be done this way
@Entity
public class PaymentMethod {
  
  @Id
  @GeneratedValue(generator = "uuid4")
  @GenericGenerator(name = "UUID", strategy = "uuid4")
  @Type(type = "org.hibernate.type.UUIDCharType")
  @Column(columnDefinition = "CHAR(36)")
  private UUID id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Enumerated(EnumType.STRING)
  @NotNull
  @Column(length = 50)
  private PaymentMethodType type;

  @NotBlank
  @Size(min = 16, max = 16)
  @Column(length = 16)
  private String number;

  @NotBlank
  private String holder;

  @NotBlank
  @Column(name = "exp_month", length = 2)
  @Size(min = 2, max = 2)
  private String expMonth;

  @NotBlank
  @Column(length = 4)
  @Size(min = 4, max = 4)
  private String expYear;

  @NotBlank
  @Column(length = 3)
  @Size(min = 3, max = 3)
  private String cvv; // :)

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
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

  public String getCvv() {
    return cvv;
  }

  public void setCvv(String cvv) {
    this.cvv = cvv;
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
    PaymentMethod other = (PaymentMethod) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}

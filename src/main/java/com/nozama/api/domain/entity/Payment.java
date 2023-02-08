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

import com.nozama.api.domain.enums.PaymentStatus;

@Entity
public class Payment {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private Order order;
  
  @ManyToOne
  @JoinColumn(name = "payment_method_id")
  private PaymentMethod paymentMethod;
  
  private BigDecimal amountPaid;
  
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;
  
  private LocalDateTime createdAt;
  
  private LocalDateTime modifiedAt;

  public Payment() {}
  
  public Payment(Order order, PaymentMethod paymentMethod, BigDecimal amountPaid, PaymentStatus status) {
    this.order = order;
    this.paymentMethod = paymentMethod;
    this.amountPaid = amountPaid;
    this.status = status;
    this.createdAt = LocalDateTime.now();
    this.modifiedAt = LocalDateTime.now();
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Order getOrder() {
    return order;
  }
  public void setOrder(Order order) {
    this.order = order;
  }
  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }
  public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
  public BigDecimal getAmountPaid() {
    return amountPaid;
  }
  public void setAmountPaid(BigDecimal amountPaid) {
    this.amountPaid = amountPaid;
  }
  public PaymentStatus getStatus() {
    return status;
  }
  public void setStatus(PaymentStatus status) {
    this.status = status;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }
  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }
  
}

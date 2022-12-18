package com.nozama.api.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.nozama.api.domain.validation.EighteenOrOlder;

@Entity
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  @NotBlank(message = "The full name is required.")
  @Size(max = 255, message = "The full name must not exceed 255 characters.")
  private String fullName;

  @NotNull(message = "The birth date is required.")
  @Past
  @EighteenOrOlder(message = "You must be eighteen or older to register.")
  private LocalDate birthDate;

  @NotBlank(message = "The CPF is required.")
  @CPF(message = "The informed CPF is not valid. It must contain exactly 14 characters, including dots and one hyphen. Example: 111.222.333-00.")
  @Column(unique = true)
  private String cpf;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Address> addresses = new ArrayList<>();

  private LocalDate registeredAt;

  public Customer() {}
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public LocalDate getRegisteredAt() {
    return registeredAt;
  }

  public void setRegisteredAt(LocalDate registeredAt) {
    this.registeredAt = registeredAt;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
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
    Customer other = (Customer) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  
}

package com.nozama.api.application.dto.request.authentication;

import java.time.LocalDate;

public class RegistrationRequest {

  private String email;
  private String password;
  private String fullName;
  private LocalDate birthDate;  
  private String cpf;
  
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
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
    
}
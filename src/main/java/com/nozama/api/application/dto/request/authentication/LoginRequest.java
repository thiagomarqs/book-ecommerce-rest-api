package com.nozama.api.application.dto.request.authentication;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
  @NotBlank(message = "The e-mail is required.")
  private String email;

  @NotBlank(message = "The password is required.")
  private String password;
  
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
}

package com.nozama.api.domain.entity.builder;

import java.util.List;

import com.nozama.api.domain.entity.User;
import com.nozama.api.domain.entity.Role;
import com.nozama.api.domain.enums.RoleName;

public class UserBuilder {

  private final User user = new User();

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public UserBuilder withEmail(String email) {
    this.user.setEmail(email);
    return this;
  }

  public UserBuilder withPassword(String password) {
    this.user.setPassword(password);
    return this;
  }

  public UserBuilder withRoles(List<RoleName> roles) {
    List<Role> userRoles = roles
      .stream()
      .map(role -> new Role("ROLE_".concat(role.name())))
      .toList();

    this.user.setRoles(userRoles);

    return this;
  }

  public User build() {
    return this.user;
  }

}

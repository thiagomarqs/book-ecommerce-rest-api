package com.nozama.api.application.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.application.dto.request.authentication.LoginRequest;
import com.nozama.api.application.dto.request.authentication.RegistrationRequest;
import com.nozama.api.application.dto.response.LoginResponse;
import com.nozama.api.domain.entity.builder.CustomerBuilder;
import com.nozama.api.domain.entity.builder.UserBuilder;
import com.nozama.api.domain.usecase.customer.ManageCustomer;
import com.nozama.api.infrastructure.security.jwt.JwtTokenUtils;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations for authentication.")
public class AuthenticationController {

  @Autowired
  private ManageCustomer manageCustomerUseCase;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtils jwtTokenUtils;
  
  @PostMapping(
    path = "/register",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
    
    var encodedPassword = encoder.encode(request.getPassword());

    var user = UserBuilder
      .builder()
      .withEmail(request.getEmail())
      .withPassword(encodedPassword)
      .build();

    var customer = CustomerBuilder
      .builder()
      .withUser(user)
      .withBirthDate(request.getBirthDate())
      .withCpf(request.getCpf())
      .withFullName(request.getFullName())
      .build();
      
    customer.setRegisteredAt(LocalDate.now());

    manageCustomerUseCase.register(customer, user);

    return ResponseEntity.ok().build();
    
  }

  @PostMapping(
    path = "/login",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    var authRequest = new UsernamePasswordAuthenticationToken(
      request.getEmail(),
      request.getPassword()
    );

    authenticationManager.authenticate(authRequest);

    var user = UserBuilder
      .builder()
      .withEmail(request.getEmail())
      .build();

    var token = jwtTokenUtils.generateToken(user);

    var response = new LoginResponse(token);
    
    return ResponseEntity.ok(response);
  }
  
}

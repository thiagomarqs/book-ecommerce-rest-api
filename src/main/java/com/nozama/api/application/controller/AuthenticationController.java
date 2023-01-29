package com.nozama.api.application.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.application.dto.request.authentication.LoginRequest;
import com.nozama.api.application.dto.request.authentication.RegistrationRequest;
import com.nozama.api.application.dto.response.login.LoginResponse;
import com.nozama.api.application.exception.AuthenticationException;
import com.nozama.api.application.service.JwtService;
import com.nozama.api.domain.entity.builder.CustomerBuilder;
import com.nozama.api.domain.entity.builder.UserBuilder;
import com.nozama.api.domain.usecase.customer.ManageCustomer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
  private JwtService jwtService;
  
  @PostMapping(
    path = "/register",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Registers a new customer.",
    description = "Registers a new customer with the provided information. A user will be created for this new customer.",
    tags = { "Authentication" }
  )
  public ResponseEntity<?> register(@RequestBody @Valid @Parameter(description = "The registration request. Contains all the necessary details to register a new customer.")  RegistrationRequest request) {
    
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

    final var uri = Link.of("/api/auth/login").toUri();

    return ResponseEntity.created(uri).body(null);
  }

  @PostMapping(
    path = "/login",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Login",
    description = "Logs in a customer. If the provided credentials aren't valid, an exception will be thrown.",
    tags = { "Authentication" }
  )
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid @Parameter(description = "The user's e-mail and password.") LoginRequest request) {
    var authRequest = new UsernamePasswordAuthenticationToken(
      request.getEmail(),
      request.getPassword()
    );
 
    try {
      authenticationManager.authenticate(authRequest);
    }
    catch(BadCredentialsException e) {
      throw new AuthenticationException("Invalid e-mail or password. Please try again");
    }

    var user = UserBuilder
      .builder()
      .withEmail(request.getEmail())
      .build();

    var token = jwtService.generateToken(user);

    var response = new LoginResponse(token);
    
    return ResponseEntity.ok(response);
  }
  
}

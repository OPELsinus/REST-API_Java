package com.joskiy.arcane.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.joskiy.arcane.models.UsersData;
import com.joskiy.arcane.payload.request.LoginRequest;
import com.joskiy.arcane.payload.request.SignupRequest;
import com.joskiy.arcane.payload.response.JwtResponse;
import com.joskiy.arcane.payload.response.MessageResponse;
import com.joskiy.arcane.repo.UsersDataRepository;
import com.joskiy.arcane.security.jwt.JwtUtils;
import com.joskiy.arcane.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UsersDataRepository usersDataRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  public AuthController(UsersDataRepository usersDataRepository) {
    this.usersDataRepository = usersDataRepository;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> role = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         role.get(0)));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    System.out.println("SIGNUP!!!");
    if (usersDataRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (usersDataRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    UsersData user = new UsersData(signUpRequest.getUsername(),
                                   encoder.encode(signUpRequest.getPassword()),
                                   signUpRequest.getEmail());


    user.setRole(signUpRequest.getRole());
    user.setFirst_name(signUpRequest.getFirst_name());
    user.setLast_name(signUpRequest.getLast_name());
    usersDataRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
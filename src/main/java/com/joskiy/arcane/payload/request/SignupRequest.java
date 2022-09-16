package com.joskiy.arcane.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;


  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  private String first_name;

  @NotBlank
  private String last_name;

  @NotBlank
  private String role;
}

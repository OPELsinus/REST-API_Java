package com.joskiy.arcane.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @GetMapping("/all")
  public String allAccess(HttpSession session, HttpServletRequest request) {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasAnyAuthority('user') or hasAnyAuthority('inspector') or hasAnyAuthority('moderator') or hasAnyAuthority('admin')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasAnyAuthority('moderator') or hasAnyAuthority('admin')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAnyAuthority('admin')")
  public String adminAccess() {
    return "Admin Board.";
  }
}
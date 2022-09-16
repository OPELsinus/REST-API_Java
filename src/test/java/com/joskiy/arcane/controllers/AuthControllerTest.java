package com.joskiy.arcane.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joskiy.arcane.payload.request.SignupRequest;
import com.joskiy.arcane.repo.*;
import com.joskiy.arcane.security.WebSecurityConfig;
import com.joskiy.arcane.security.services.UserDetailsServiceImpl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.joskiy.arcane.models.UsersData;
import com.joskiy.arcane.payload.request.LoginRequest;
import com.joskiy.arcane.security.jwt.JwtUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring5.expression.Mvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class AuthControllerTest {

    private MockMvc mockMvc;

    private JwtUtils jwtUtils = new JwtUtils();

    @Mock
    private AuthController authController;

    @Mock TestController testController;

    private ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(testController).build();

    }

    @Test
    public void testSignUp() throws Exception {
        SignupRequest request = new SignupRequest("TestSignUp", "123456789", "test@sign.up", "TestSignUp", "TestSignUp", "admin");
//
//        String jsonRequest = mapper.writeValueAsString(request);
//
//        System.out.println(jsonRequest);
//
//        MvcResult result = mockMvc.perform(post("http://localhost:8080/api/auth/signup").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        MvcResult result = mockMvc.perform(get("/api/test/user")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testSignIn() throws Exception {
        LoginRequest request = new LoginRequest("Drake", "123456789");

        String jsonRequest = mapper.writeValueAsString(request);

        System.out.println(jsonRequest);

        System.out.println(jwtUtils.validateJwtToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrZWsxIiwiaWF0IjoxNjYwODg5NDE1LCJleHAiOjE2NjA5NzU4MTV9.x74HMqOOass-5OMnnAtjd4ePPIi8ohBAGjtjhBKLxyxaUa4LgW3u3oI5WJCdCJriln_peptspSlkF2a9X-_ZmQ"));
    }
}
package com.joskiy.arcane.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String username;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String role;


    public UsersData(String username, String password, String email, String first_name, String last_name, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
    }

    public UsersData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}

package org.example.project_6_paymybuddy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Array;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {

    @Id
    private int id;
    public float balance;
    private String username, email, password, token;

    public User() {
    }
    public User(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }
}

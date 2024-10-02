package org.example.project_6_paymybuddy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.sql.Array;

@Entity
@Table(name="users")
@Getter
public class User {

    @Id
    public int id;
    public float balance;
    public String username, email, password, token;

    public User() {
    }
    public User(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }
}

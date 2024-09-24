package org.example.project_6_paymybuddy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    private int id;

    int senderId, beneficiaryId;
    String description;
    float amount;


}

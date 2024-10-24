package org.example.project_6_paymybuddy.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="transactions")
@Getter
@Setter
public class Transaction {
    @Id
    private int id;
    private int sender, receiver;
    private long timestamp;
    private float amount;
    private String label;

    @Transient
    String receiverUsername;
    @Transient
    String amountString;
}

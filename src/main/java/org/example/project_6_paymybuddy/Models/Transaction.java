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
    int sender, receiver;
    long timestamp;
    int amount;
    String label;

    @Transient
    String receiverUsername;
    @Transient
    String amountString;
}

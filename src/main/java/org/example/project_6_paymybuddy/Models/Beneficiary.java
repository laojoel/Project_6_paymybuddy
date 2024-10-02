package org.example.project_6_paymybuddy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name="beneficiaries")
@Getter
public class Beneficiary {
    @Id
    private int id;
    private int holder, relation;
}

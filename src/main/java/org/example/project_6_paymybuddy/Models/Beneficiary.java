package org.example.project_6_paymybuddy.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="beneficiaries")
public class Beneficiary {
    @Id
    private int id;

}

package org.example.project_6_paymybuddy.Proxies;

import org.example.project_6_paymybuddy.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TransactionProxy extends JpaRepository<User,Long> {

}

package org.example.project_6_paymybuddy.Proxies;

import jakarta.transaction.Transactional;
import org.example.project_6_paymybuddy.Models.Beneficiary;
import org.example.project_6_paymybuddy.Models.Transaction;
import org.example.project_6_paymybuddy.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.project_6_paymybuddy.Constant.*;

@Component
public interface TransactionProxy extends JpaRepository<User,Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO transactions (sender, receiver, timestamp, amount, label) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void recordTransaction(int holder, int receiver, long timestamp, float amount, String label);

    @Query("SELECT t FROM Transaction t WHERE t.sender = :sender")
    List<Transaction> findAllTransactionSentFromUserId(@Param("sender") int sender);
}

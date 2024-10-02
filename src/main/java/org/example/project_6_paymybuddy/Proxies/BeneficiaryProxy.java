package org.example.project_6_paymybuddy.Proxies;

import jakarta.transaction.Transactional;
import org.example.project_6_paymybuddy.Models.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BeneficiaryProxy extends JpaRepository<Beneficiary,Long> {

    @Query("SELECT b FROM Beneficiary b WHERE b.holder = :holder AND b.relation = :relation")
    Beneficiary findBeneficiaryByIds(@Param("holder") int holder, @Param("relation") int relation);

    @Query("SELECT b FROM Beneficiary b WHERE b.holder = :holder")
    List<Beneficiary> findAllBeneficiariesForUserId(@Param("holder") int holder);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO beneficiaries (holder, relation) VALUES (?1, ?2)", nativeQuery = true)
    void addBeneficiary(int holder, int relation);
}
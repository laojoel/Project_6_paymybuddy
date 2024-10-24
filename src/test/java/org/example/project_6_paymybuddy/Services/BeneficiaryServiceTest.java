package org.example.project_6_paymybuddy.Services;

import org.example.project_6_paymybuddy.Models.Beneficiary;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.BeneficiaryProxy;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.project_6_paymybuddy.Constant.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeneficiaryServiceTest {
    @InjectMocks
    private BeneficiaryService beneficiaryService;

    @Mock
    private BeneficiaryProxy beneficiaryProxy;
    @Mock
    private UserProxy userProxy;

    // Add Relation Success
    @Test
    void addBeneficiarySuccessTest() {
        when(userProxy.findUserWithEmail(anyString())).thenReturn(new User());
        when(beneficiaryProxy.findBeneficiaryByIds(anyInt(), anyInt())).thenReturn(null);
        assertThat(beneficiaryService.addBeneficiary(1, "test@mail.com")).isEqualTo(ADD_BENEFICIARY_SUCCESS);
    }

    // Add Relation Fail: Duplicated
    @Test
    void addBeneficiaryFailDuplicatedTest() {
        when(userProxy.findUserWithEmail(anyString())).thenReturn(new User());
        when(beneficiaryProxy.findBeneficiaryByIds(anyInt(), anyInt())).thenReturn(new Beneficiary());
        assertThat(beneficiaryService.addBeneficiary(1, "test@mail.com")).isEqualTo(ADD_BENEFICIARY_DUPLICATED);
    }

    // Add Relation Fail: Unknown user email
    @Test
    void addBeneficiaryFailUnknownTest() {
        when(userProxy.findUserWithEmail(anyString())).thenReturn(null);
        assertThat(beneficiaryService.addBeneficiary(1, "test@mail.com")).isEqualTo(ADD_BENEFICIARY_UNKNOWN_EMAIL);
    }

    @Test
    void getUsersBeneficiariesForUserIdTest() {
        List<User> users = new ArrayList<>();
        User userA = new User();
        userA.setUsername("John");
        users.add(userA);
        User userB = new User();
        userB.setUsername("Alice");
        users.add(userB);

        when(beneficiaryProxy.findAllBeneficiariesForUserId(anyInt())).thenReturn(new ArrayList<>());
        when(userProxy.findUsersFromIdArray(anyList())).thenReturn(users);

        List<User> result = beneficiaryService.getUsersBeneficiariesForUserId(711);
        assertThat(result).isNotNull();
        assertThat(result.get(0).getUsername()).isEqualTo("Alice");
        assertThat(result.get(1).getUsername()).isEqualTo("John");
    }

}

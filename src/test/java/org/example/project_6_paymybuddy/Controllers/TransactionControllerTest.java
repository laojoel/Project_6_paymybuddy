package org.example.project_6_paymybuddy.Controllers;

import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.BeneficiaryService;
import org.example.project_6_paymybuddy.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringJUnitConfig
@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private  TransactionService transactionService;
    @Mock
    private  BeneficiaryService beneficiaryService;
    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void getTransactionTest() throws Exception {
        User user = new User(); user.setId(1);

        when(beneficiaryService.getUsersBeneficiariesForUserId(1)).thenReturn(Collections.emptyList());
        when(transactionService.getTransactionSentFromUserID(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transaction").requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attributeExists("color"))
                .andExpect(model().attribute("transaction", Collections.emptyList()))
                .andExpect(view().name("transactionView"));
    }

    @Test
    void postTransactionTest() throws Exception {
        String relation = "text@mail.net", label="restaurant", amount = "512";
        User user = new User(); user.setId(1);

        when(beneficiaryService.getUsersBeneficiariesForUserId(1)).thenReturn(Collections.emptyList());
        when(transactionService.getTransactionSentFromUserID(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/transaction")
                        .param("relation", relation)
                        .param("label", label)
                        .param("amount", amount)
                        .requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attribute("msg", "Transaction de 512 € réalisé avec auccès"))
                .andExpect(model().attributeExists("color"))
                .andExpect(model().attribute("color", "#9ADE8FFF"))
                .andExpect(view().name("transactionView"));
    }
}

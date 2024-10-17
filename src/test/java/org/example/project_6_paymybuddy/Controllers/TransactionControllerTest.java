package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import org.example.project_6_paymybuddy.Models.Transaction;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.example.project_6_paymybuddy.Services.BeneficiaryService;
import org.example.project_6_paymybuddy.Services.TransactionService;
import org.example.project_6_paymybuddy.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {

    //@Autowired
    //MockMvc mockMvc;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;
    @Mock
    private BeneficiaryService beneficiaryService;

    MockHttpServletRequest request;
    @BeforeEach
    void setup() {
        request = new MockHttpServletRequest();
    }

    @Test
    void getTransactionTest() throws Exception {
        User user = new User(); user.setId(1);
        //request.setAttribute("user", user);

        when(beneficiaryService.getUsersBeneficiariesForUserId(anyInt())).thenReturn(Arrays.asList(new User(), new User()));
        when(transactionService.getTransactionSentFromUserID(anyInt())).thenReturn(Arrays.asList(new Transaction(), new Transaction()));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        mockMvc.perform(get("/transaction").requestAttr("user", user)).andExpect(status().isOk());

        /*
        User user = new User(); user.id=1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("user", new User());

        when(beneficiaryService.getUsersBeneficiariesForUserId(user.getId())).thenReturn(Arrays.asList(new User(), new User()));
        when(transactionService.getTransactionSentFromUserID(user.getId())).thenReturn(Arrays.asList(new Transaction(), new Transaction()));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        mockMvc.perform(get("/transaction").requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attributeExists("color"))
                .andExpect(model().attributeExists("users"));
                //.andExpect(model().attributeExists("transaction"))
                //.andExpect(view().name("transaction"));
         */


        /*
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in webapp-mode (same config as spring-boot)
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        User user = new User(); // Create a user for testing
        user.setId(123); // Set user ID

        List<User> users = Arrays.asList(new User(), new User());
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());
        when(beneficiaryService.getUsersBeneficiariesForUserId(123)).thenReturn(users);
        when(transactionService.getTransactionSentFromUserID(123)).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.model().attribute("transaction", transactions))
                .andExpect(MockMvcResultMatchers.view().name("transaction"));

         */
    }
}

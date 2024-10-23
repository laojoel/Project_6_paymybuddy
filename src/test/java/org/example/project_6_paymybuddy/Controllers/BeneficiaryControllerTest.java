package org.example.project_6_paymybuddy.Controllers;

import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.BeneficiaryService;
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

import static org.example.project_6_paymybuddy.Constant.ADD_BENEFICIARY_SUCCESS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig
@ExtendWith(SpringExtension.class)
public class BeneficiaryControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BeneficiaryService beneficiaryService;
    @InjectMocks
    private BeneficiaryController beneficiaryController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(beneficiaryController).build();
    }

    @Test
    void getBeneficiaryTest() throws Exception {
        mockMvc.perform(get("/beneficiary"))
                .andExpect(status().isOk())
                .andExpect(view().name("beneficiaryView"));
    }

    @Test
    void postAddBeneficiary() throws Exception {
        String email = "test@mail.net";
        User user = new User(); user.setId(1);

        when(beneficiaryService.addBeneficiary(user.getId(), email)).thenReturn(ADD_BENEFICIARY_SUCCESS);

        mockMvc.perform(post("/beneficiary").param("email", email).requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attribute("msg", "Bénéficiaire test@mail.net ajouté avec succès"))
                .andExpect(model().attributeExists("color"))
                .andExpect(model().attribute("color", "#339933"))
                .andExpect(view().name("beneficiaryView"));

        verify(beneficiaryService, times(1)).addBeneficiary(user.getId(), email);
    }
}

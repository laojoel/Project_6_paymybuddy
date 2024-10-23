package org.example.project_6_paymybuddy.Controllers;

import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.UserService;
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

import static org.example.project_6_paymybuddy.Constant.USER_CREATION_SUCCESS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringJUnitConfig
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getSignInTest() throws Exception {
        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk())
                .andExpect(view().name("signinView"));
    }

    // Signin Success
    @Test
    void postSignInTest() throws Exception {
        User user = new User(); user.setId(1); user.setToken("abc");

        when(userService.authenticateUser(anyString(), anyString())).thenReturn(user);

        mockMvc.perform(post("/signin")
                        .param("email", "test@mail.net")
                        .param("password", "a password"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("id"))
                .andExpect(model().attributeExists("token"))
                .andExpect(view().name("transactionView"));
    }
    // Signin fail
    @Test
    void postSignInTestFail() throws Exception {
        when(userService.authenticateUser(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(post("/signin")
                        .param("email", "test@mail.net")
                        .param("password", "a password"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attributeExists("color"))
                .andExpect(view().name("signinView"));
    }

    @Test
    void getSignUpTest() throws  Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signupView"));
    }

    @Test
    void postSignUpTest() throws Exception {
        String username="John", email="text@mail.net", password="abc123456";
        when(userService.createNewUser(username, email, password)).thenReturn(USER_CREATION_SUCCESS);

        mockMvc.perform(post("/signup")
                        .param("username", username)
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attribute("msg", "Compte crée avec succès"))
                .andExpect(model().attributeExists("color"))
                .andExpect(model().attribute("color", "#339933"))
                .andExpect(view().name("signinView"));
    }

    @Test
    void getLogoutTest() throws Exception {
        User user = new User();

        mockMvc.perform(get("/logout")
                        .requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attributeExists("color"))
                .andExpect(view().name("signinView"));
    }

    @Test
    void getProfileTest() throws Exception {
        User user = new User(); user.setUsername("john"); user.setEmail("test@mail.net");

        mockMvc.perform(get("/profile")
                        .requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attributeExists("password"))
                .andExpect(view().name("profileView"));
    }

    @Test
    void postProfileTest() throws Exception {
        User user = new User(); user.setUsername("john"); user.setEmail("test@mail.net"); user.setPassword("abc123456");

        mockMvc.perform(post("/profile")
                        .param("username", "John")
                        .param("email", "text@mail.net")
                        .param("password", "abc123456")
                        .requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("profileView"));

        verify(userService, times(1)).updateUserProfile(user.getId(), "John", "text@mail.net", "abc123456");
    }

    @Test
    void getNavbarTest() throws Exception {
        User user = new User(); user.setBalance(256);
        mockMvc.perform(get("/navbar/transaction")
                        .requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("balance"))
                .andExpect(model().attribute("balance", "Solde: 256 €"))
                .andExpect(view().name("navbarView"));
    }

    @Test
    void postCredit() throws Exception {
        User user = new User(); user.setId(1);
        mockMvc.perform(post("/credit")
                .requestAttr("user", user)
                .param("amount", "512"))
                .andExpect(status().isOk());
        verify(userService, times(1)).addCredit(user.getId(), 512);
    }
}

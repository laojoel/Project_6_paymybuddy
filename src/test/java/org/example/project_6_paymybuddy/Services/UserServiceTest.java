package org.example.project_6_paymybuddy.Services;

import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.project_6_paymybuddy.Constant.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserProxy userProxy;

    // New User Creation success
    @Test
    void createNewUserTest() {
        String username = "johnSmith"; String email = "net@mail.com";
        when(userProxy.findUserByMailOrUsername(anyString(), anyString())).thenReturn(null);
        byte result = userService.createNewUser(username, email, "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9");
        assertThat(result).isEqualTo(USER_CREATION_SUCCESS);
    }

    // New User Creation Fail: Low username char count
    @Test
    void createNewUserTest_failShortUsername() {
        String username = "ab"; String email = "net@mail.com";
        byte result = userService.createNewUser(username, email, "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9");
        assertThat(result).isEqualTo(USER_CREATION_WRONG_INPUTS);
    }
    // New User Creation Fail: High username char count
    @Test
    void createNewUserTest_failLongUsername() {
        String username = "This is a long username"; String email = "net@mail.com";
        byte result = userService.createNewUser(username, email, "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9");
        assertThat(result).isEqualTo(USER_CREATION_WRONG_INPUTS);
    }
    // New User Creation Fail: username isn't Alphabetic
    @Test
    void createNewUserTest_failUsernameNotAlpha() {
        String username = "A711"; String email = "net@mail.com";
        byte result = userService.createNewUser(username, email, "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9");
        assertThat(result).isEqualTo(USER_CREATION_WRONG_INPUTS);
    }
    // New User Creation Fail: password's hash issue
    @Test
    void createNewUserTest_failPasswordHash() {
        String username = "johnSmith"; String email = "net@mail.com";
        byte result = userService.createNewUser(username, email, "HashIssue");
        assertThat(result).isEqualTo(USER_CREATION_WRONG_INPUTS);
    }

    // New User Creation Fail: mail issue (too short)
    @Test
    void createNewUserTest_failShortMail() {
        String username = "johnSmith"; String email = "@a";
        byte result = userService.createNewUser(username, email, "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9");
        assertThat(result).isEqualTo(USER_CREATION_WRONG_INPUTS);
    }

    // New User Creation Fail: mail issue (too long)
    @Test
    void createNewUserTest_failLongMail() {
        String username = "johnSmith"; String email = "ThisEmailIsWayTooLong@mail.com";
        byte result = userService.createNewUser(username, email, "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9");
        assertThat(result).isEqualTo(USER_CREATION_WRONG_INPUTS);
    }

    // New User Creation Fail: Duplicated user
    @Test
    void createNewUserTest_failDuplicated() {
        String username = "johnSmith"; String email = "net@mail.com";
        when(userProxy.findUserByMailOrUsername(anyString(), anyString())).thenReturn(new User());
        byte result = userService.createNewUser(username, email, "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9");
        assertThat(result).isEqualTo(USER_CREATION_ALREADY_EXIST);
    }

    // Authentication Success
    @Test
    void authenticateUserTest() {
        when(userProxy.authenticateUser(anyString(), anyString())).thenReturn(new User("johnSmith", "net@mail.com", "f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9"));
        User result = userService.authenticateUser(anyString(), anyString());
        assertThat(result).isNotNull();
        assertThat(result.getToken()).isNotNull();
        assertThat(result.getToken().length()).isEqualTo(43);
    }

    // Authentication Failed
    @Test
    void authenticateUserTest_Fail() {
        when(userProxy.authenticateUser(anyString(), anyString())).thenReturn(null);
        User result = userService.authenticateUser(anyString(), anyString());
        assertThat(result).isNull();
    }

    // Successfully retrieve a User from a Token
    @Test
    void getUserWithTokenTest() {
        when(userProxy.findUserWithToken(anyString())).thenReturn(new User());
        User result = userService.getUserWithToken("opko84q684v89w1684svd4qc12zpo3c17kosj84ytp5");
        assertThat(result).isNotNull();
    }

    // Fail to retrieve a User from Null Token Size
    @Test
    void getUserWithTokenTest_failNullToken() {
        User result = userService.getUserWithToken(null);
        assertThat(result).isNull();
    }

    // Fail to retrieve a User from invalid Token Size
    @Test
    void getUserWithTokenTest_failTokenSize() {
        User result = userService.getUserWithToken("opko84q684v89w");
        assertThat(result).isNull();
    }

    // Fail to retrieve a User from Token
    @Test
    void getUserWithTokenTest_failToken() {
        when(userProxy.findUserWithToken(anyString())).thenReturn(null);
        User result = userService.getUserWithToken("opko84q684v89w1684svd4qc12zpo3c17kosj84ytp5");
        assertThat(result).isNull();
    }

    @Test
    void revokeTokenTest() {
        userService.revokeToken(1);
        verify(userProxy).setToken(eq(1), eq("x"));
    }

    @Test
    void addCreditTest() {
        userService.addCredit(1,512);
        verify(userProxy).setCredit(eq(1), eq(512.0f));

    }

    @Test
    void updateUserProfileTest() {
        userService.updateUserProfile(1,"johnSmith", "net@mail.com", "pass");
        verify(userProxy).updateUserProfile(eq(1), eq("johnSmith"), eq("net@mail.com"), eq("pass"));

    }

}

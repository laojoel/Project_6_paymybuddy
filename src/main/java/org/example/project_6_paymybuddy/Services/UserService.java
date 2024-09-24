package org.example.project_6_paymybuddy.Services;

import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

import static org.example.project_6_paymybuddy.Constant.*;

@Service
public class UserService {

    @Autowired
    private UserProxy userProxy;
    SecureRandom secureRandom = new SecureRandom();

    public byte createNewUser(String username, String email, String password) {
        if (username.length() < USERNAME_MIN_LEN || username.length() > USERNAME_MAX_LEN || !username.chars().allMatch(Character::isAlphabetic)) {return USER_CREATION_WRONG_INPUTS;}
        else if (password.length() < PASSWORD_MIN_LEN || password.length() > PASSWORD_MAX_LEN) {return USER_CREATION_WRONG_INPUTS;}
        else if (email.length() < MAIL_MIN_LEN || email.length() > MAIL_MAX_LEN || !email.contains("@")) {return USER_CREATION_WRONG_INPUTS;}
        else if (userProxy.findUserByMailOrUsername(email,username) != null) {return USER_CREATION_ALREADY_EXIST;}
        else {userProxy.createUser(username,email,password); return  USER_CREATION_SUCCESS;}
    }

    public String authenticateUser(String email, String password) {
        User user = userProxy.authenticateUser(email, password);
        if (user == null) {
            return null;
        }
        else {
            byte[] tokenBuffer = new byte[32]; // 32 bytes = 256 bits
            secureRandom.nextBytes(tokenBuffer);
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBuffer);
            userProxy.setToken(user.id, token);
            return token;
        }
    }
}

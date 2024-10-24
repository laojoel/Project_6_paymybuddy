package org.example.project_6_paymybuddy.Services;

import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Proxies.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

import static org.example.project_6_paymybuddy.Constant.*;
import static org.example.project_6_paymybuddy.ApplicationStarter.logger;

@Service
public class UserService {

    @Autowired
    private UserProxy userProxy;
    SecureRandom secureRandom = new SecureRandom();

    public byte createNewUser(String username, String email, String password) {
        if (username.length() < USERNAME_MIN_LEN || username.length() > USERNAME_MAX_LEN || !username.chars().allMatch(Character::isAlphabetic)) {return USER_CREATION_WRONG_INPUTS;}
        else if (password.length() != PASSWORD_HASH_LEN) {return USER_CREATION_WRONG_INPUTS;}
        else if (email.length() < MAIL_MIN_LEN || email.length() > MAIL_MAX_LEN || !email.contains("@")) {return USER_CREATION_WRONG_INPUTS;}
        else if (userProxy.findUserByMailOrUsername(email,username) != null) {return USER_CREATION_ALREADY_EXIST;}
        else {userProxy.createUser(username,email,password); logger.info("# New user created: " + username); return  USER_CREATION_SUCCESS;}
    }

    public User authenticateUser(String email, String password) {
        User user = userProxy.authenticateUser(email, password);
        if (user == null) {
            return null;
        }
        else {
            byte[] tokenBuffer = new byte[TOKEN_BYTE_SIZE];
            secureRandom.nextBytes(tokenBuffer);
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBuffer);
            userProxy.setToken(user.getId(), token);
            user.setToken(token);
            return user;
        }
    }

    public void revokeToken(int id) {
        userProxy.setToken(id, "x");
    }

    public User getUserWithToken(String token) {
        if (token==null) {return null;}
        else if (token.length() != TOKEN_STRING_LEN) {return null;}
        else {return userProxy.findUserWithToken(token);}
    }

    public void addCredit(int userId, int amount) {
        userProxy.setCredit(userId, (float)amount);
        logger.info("# UserId" + userId + "Credited " + amount + " € to his/her account");
    }

    public void updateUserProfile(int id, String username, String email, String password) {
        if (password.equals("false")){userProxy.updateUserProfileNoPassword(id, username, email);}
        else {userProxy.updateUserProfile(id, username,email,password);}
    }

}

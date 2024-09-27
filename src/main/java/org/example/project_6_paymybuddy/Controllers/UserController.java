package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.example.project_6_paymybuddy.Constant.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    //public AuthentController(UserService userService) {this.userService = userService;}

    @GetMapping("/signin")
    public String getSignIn(Model model) {
        model.addAttribute("msg", " ");
        model.addAttribute("color", "#339933");
        return "signin";
    }

    @PostMapping("/signin")
    public String PostSignIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpServletResponse response) {
        User user = userService.authenticateUser(email, password);
        if (user == null) {
            System.out.println("fail");
            model.addAttribute("msg", "mail et/ou mot de passe erroné(s)");
            model.addAttribute("color", "#cc3823");
            return "signin";
        }
        else {
            Cookie cookie = new Cookie("token", user.token);
            response.addCookie(cookie);
            model.addAttribute("id", user.id);
            model.addAttribute("token", user.token);
            return "transaction";
        }
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String PostSignUp(@RequestParam("username") String userName, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        String page = "", message = "", color = "";
        byte userCreationCode = userService.createNewUser(userName, email, password);
        System.out.println("Code = " + userCreationCode);
        if (userCreationCode == USER_CREATION_WRONG_INPUTS) {page="signup"; message = "Les informations saisie sont érroné"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_ALREADY_EXIST) {page="signup"; message = "Utilisateur déjà existant"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_SUCCESS) {page="signin"; message = "Compte crée avec succès"; color = "#339933";}
        model.addAttribute("msg", message);
        model.addAttribute("color", color);
        return page;
    }

    @GetMapping("/logout")
    public String getLogout(Model model, HttpServletRequest request) {
        User user = userService.getUserWithToken((String)request.getAttribute("token"));
        if(user!=null){userService.revokeToken(user.id);}

        model.addAttribute("msg", "Vous avez été déconnecté avec succès");
        model.addAttribute("color", "#339933");
        return "signin";
    }

    @GetMapping("/navbar")
    public String getNavBar() {
        return "navbar";
    }

    @GetMapping("/profile")
    public String getProfile(HttpServletRequest request, Model model) {
        User user = userService.getUserWithToken((String)request.getAttribute("token"));
        model.addAttribute("username", user.username);
        model.addAttribute("email", user.email);
        model.addAttribute("password", "**********");
        if (user!=null) {
            return "profile";
        }
        else {
            return "signin";
        }
    }

    @PostMapping("/profile")
    public String PostSignIn(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpServletResponse response, HttpServletRequest request) {
        User user = userService.getUserWithToken((String)request.getAttribute("token"));
        if (user!=null) {
            System.out.println(username + " | " + email + " | " + password);
            //userService.updateUserProfile(user.id, username, password, email);
            return "profile";
        }
        else {
            return "signin";
        }
    }


}

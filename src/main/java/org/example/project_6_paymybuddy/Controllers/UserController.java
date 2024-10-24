package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.example.project_6_paymybuddy.Constant.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public String getSignIn(Model model) {
        model.addAttribute("msg", " ");
        model.addAttribute("color", "#339933");
        return "signinView";
    }

    @PostMapping("/signin")
    public String postSignIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpServletResponse response) {
        User user = userService.authenticateUser(email, password);
        if (user == null) {
            model.addAttribute("msg", "mail et/ou mot de passe erroné(s)");
            model.addAttribute("color", "#cc3823");
            return "signinView";
        }
        else {
            Cookie cookie = new Cookie("token", user.getToken());
            response.addCookie(cookie);
            model.addAttribute("id", user.getId());
            model.addAttribute("token", user.getToken());
            return "transactionView";
        }
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signupView";
    }

    @PostMapping("/signup")
    public String postSignUp(@RequestParam("username") String userName, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        String page = "", message = "", color = "";
        byte userCreationCode = userService.createNewUser(userName, email, password);
        System.out.println("Code = " + userCreationCode);
        if (userCreationCode == USER_CREATION_WRONG_INPUTS) {page="signupView"; message = "Les informations saisie sont érroné"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_ALREADY_EXIST) {page="signupView"; message = "Utilisateur déjà existant"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_SUCCESS) {page="signinView"; message = "Compte crée avec succès"; color = "#339933";}
        model.addAttribute("msg", message);
        model.addAttribute("color", color);
        return page;
    }

    @GetMapping("/logout")
    public String getLogout(Model model, HttpServletRequest request) {
        userService.revokeToken(((User)request.getAttribute("user")).getId());
        model.addAttribute("msg", "Vous avez été déconnecté avec succès");
        model.addAttribute("color", "#339933");
        return "signinView";
    }

    @GetMapping("/profile")
    public String getProfile(HttpServletRequest request, Model model) {
        User user = (User)request.getAttribute("user");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", "**********");
        return "profileView";
    }

    @PostMapping("/profile")
    public String postProfile(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password,HttpServletRequest request, Model model) {
        User user = (User)request.getAttribute("user");
        userService.updateUserProfile(user.getId(), username, email, password);
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("password", "**********");
        return "profileView";
    }

    @GetMapping("/navbar/{page}")
    public String getNavBar(Model model, HttpServletRequest request, @PathVariable("page") String page) {
        User user = (User)request.getAttribute("user");
        model.addAttribute("balance", "Solde: " + user.balance + " €");
        model.addAttribute("page", page);
        return "navbarView";
    }

    @PostMapping("/credit")
    public ResponseEntity<Object> postCredit(@RequestParam("amount") String amount, HttpServletRequest request) {
        User user=(User)request.getAttribute("user");
        userService.addCredit(user.getId(), Integer.parseInt(amount));
        return ResponseEntity.ok().build();
    }
}

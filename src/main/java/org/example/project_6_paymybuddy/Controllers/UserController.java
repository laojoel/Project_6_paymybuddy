package org.example.project_6_paymybuddy.Controllers;

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
    public String PostSignIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {

        String token = userService.authenticateUser(email, password);
        if (token == null) {
            model.addAttribute("msg", "mail et/ou mot de passe erroné(s)");
            model.addAttribute("color", "#cc3823");
        }
        else {
            model.addAttribute("msg", "OK !");
            model.addAttribute("color", "#cc3823");
        }


        return "signin";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String PostSignUp(@RequestParam("username") String userName, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        System.out.println(userName + " | " + email + " | " + password);
        String page = "", message = "", color = "";
        byte userCreationCode = userService.createNewUser(userName, email, password);
        if (userCreationCode == USER_CREATION_WRONG_INPUTS) {page="signup"; message = "Les informations saisie sont érroné"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_ALREADY_EXIST) {page="signup"; message = "Utilisateur déjà existant"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_SUCCESS) {page="signin"; message = "Compte crée avec succès"; color = "#339933";}
        model.addAttribute("msg", message);
        model.addAttribute("color", color);
        return page;
    }
}

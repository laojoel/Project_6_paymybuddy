package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

import static org.example.project_6_paymybuddy.Constant.*;

@Controller
public class BeneficiaryController {

    @Autowired
    private UserService userService;

    @GetMapping("/beneficiary")
    public String getBeneficiary(Model model, HttpServletRequest request) {

        User user = userService.getUserWithToken((String)request.getAttribute("token"));
        if (user!=null) {
            return "beneficiary";
        }
        else {
            return "signin";
        }
    }

    @PostMapping("/beneficiary")
    public String PostSignUp(@RequestParam("email") String email, Model model, HttpServletRequest request) {
        String page = "", message = "", color = "";
        byte addBeneficiary = userService.addBeneficiary(email);
        /*
        System.out.println("Code = " + userCreationCode);
        if (userCreationCode == USER_CREATION_WRONG_INPUTS) {page="signup"; message = "Les informations saisie sont érroné"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_ALREADY_EXIST) {page="signup"; message = "Utilisateur déjà existant"; color = "#cc3823";}
        else if (userCreationCode == USER_CREATION_SUCCESS) {page="signin"; message = "Compte crée avec succès"; color = "#339933";}
        model.addAttribute("msg", message);
        model.addAttribute("color", color);
        return page;
        */
        return "";
    }
}

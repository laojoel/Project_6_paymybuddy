package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class BeneficiaryController {

    @Autowired
    private UserService userService;

    @GetMapping("/beneficiary")
    public String getSignIn(Model model, HttpServletRequest request) {

        User user = userService.getUserWithToken((String)request.getAttribute("token"));
        if (user!=null) {
            List<String> options = Arrays.asList("Max Dunoix", "Estelle Frost", "Maximum Brutus");
            model.addAttribute("options", options);
            return "beneficiary";
        }
        else {
            return "signin";
        }
    }
}

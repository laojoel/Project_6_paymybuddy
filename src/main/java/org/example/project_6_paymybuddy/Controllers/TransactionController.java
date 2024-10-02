package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.BeneficiaryService;
import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.example.project_6_paymybuddy.Constant.*;

@Controller
public class TransactionController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @GetMapping("/transaction")
    public String getSignIn(Model model, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        List<User> users = beneficiaryService.getBeneficiariesForUserId(user.getId());
        model.addAttribute("users", users);
        return "transaction";

    }
    @PostMapping("/transaction")
    public String PostSignUp(@RequestParam("email") String email, Model model, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        List<User> users = beneficiaryService.getBeneficiariesForUserId(user.getId());
        model.addAttribute("users", users);
        return "transaction";
    }
}

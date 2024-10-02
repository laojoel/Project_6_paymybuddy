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
import java.util.List;

import static org.example.project_6_paymybuddy.Constant.*;

@Controller
public class BeneficiaryController {

    @Autowired
    private UserService userService;
    @Autowired
    private BeneficiaryService beneficiaryService;

    @GetMapping("/beneficiary")
    public String getBeneficiary() {
        return "beneficiary";
    }

    @PostMapping("/beneficiary")
    public String PostSignUp(@RequestParam("email") String email, Model model, HttpServletRequest request) {
        User user =(User)request.getAttribute("user");
        String message = "", color = "";
        byte addBeneficiaryCode = beneficiaryService.addBeneficiary(user.getId() ,email);
        System.out.println("addBeneficiary Result = " + addBeneficiaryCode);

        if (addBeneficiaryCode == ADD_BENEFICIARY_DUPLICATED) {message = "Bénéficiaire déjà existant"; color = "#cc3823";}
        else if (addBeneficiaryCode == ADD_BENEFICIARY_UNKNOWN_EMAIL) {message = "Bénéficiaire inéxistant"; color = "#cc3823";}
        else if (addBeneficiaryCode == ADD_BENEFICIARY_SUCCESS) {message = "Bénéficiaire "+ email +" ajouté avec succès"; color = "#339933";}
        model.addAttribute("msg", message);
        model.addAttribute("color", color);
        return "beneficiary";
    }
}

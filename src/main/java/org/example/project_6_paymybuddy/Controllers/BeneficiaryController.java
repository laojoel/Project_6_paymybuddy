package org.example.project_6_paymybuddy.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class BeneficiaryController {
    @GetMapping("/beneficiary")
    public String getSignIn(Model model) {

        List<String> options = Arrays.asList("Max Dunoix", "Estelle Frost", "Maximum Brutus");
        model.addAttribute("options", options);
        return "beneficiary";
    }
}

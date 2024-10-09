package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.project_6_paymybuddy.Models.Transaction;
import org.example.project_6_paymybuddy.Models.User;
import org.example.project_6_paymybuddy.Services.BeneficiaryService;
import org.example.project_6_paymybuddy.Services.TransactionService;
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
    private TransactionService transactionService;
    @Autowired
    private BeneficiaryService beneficiaryService;
    @Autowired
    private UserService userService;

    @GetMapping("/transaction")
    public String getTransaction(Model model, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        model.addAttribute("msg", "");
        model.addAttribute("color", "#ffffff");

        List<User> UsersBeneficiaries = beneficiaryService.getUsersBeneficiariesForUserId(user.getId());
        model.addAttribute("users", UsersBeneficiaries);

        List<Transaction> transactions = transactionService.getTransactionSentFromUserID(user.getId());
        model.addAttribute("transaction", transactions);
        return "transaction";
    }
    @PostMapping("/transaction")
    public String postTransaction(@RequestParam("relation") String relation, @RequestParam("label") String label,@RequestParam("amount") String amount, Model model, HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        String msg="", color="";
        byte result = transactionService.proceedTransaction(user, relation, amount, label);

        if (result == TRANSACTION_FAIL_RECEIVER) {msg="Transaction échoué: le bénéficiaire est introuvable"; color = "#FF9393FF";}
        else if (result == TRANSACTION_FAIL_AMOUNT) {msg="Transaction échoué: le montant de la transaction doit être entre 1 et 8 000 €"; color = "#FF9393FF";}
        else if (result == TRANSACTION_FAIL_LABEL) {msg="Transaction échoué: le Label doit être de 2 et 20 charactères (alpha-numérique uniquement)"; color = "#FF9393FF";}
        else if (result == TRANSACTION_FAIL_BALANCE) {msg="Transaction échoué: le solde de votre compte est inssufisant"; color = "#FF9393FF";}
        else if (result == TRANSACTION_SUCCESS) {msg="Transaction de " + amount + " € réalisé avec auccès"; color = "#9ADE8FFF";}
        model.addAttribute("msg", msg);
        model.addAttribute("color", color);

        List<User> usersBeneficiaries = beneficiaryService.getUsersBeneficiariesForUserId(user.getId());
        model.addAttribute("users", usersBeneficiaries);

        List<Transaction> transactions = transactionService.getTransactionSentFromUserID(user.getId());
        model.addAttribute("transaction", transactions);

        return "transaction";
    }
}

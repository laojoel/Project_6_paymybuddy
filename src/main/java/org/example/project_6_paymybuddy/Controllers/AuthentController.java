package org.example.project_6_paymybuddy.Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthentController {

    //private final UserService userService;

    @GetMapping("/signin")
    public String getSignIn() {
        System.out.println("Get Sign In");
        return "signin";
    }
    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signin")
    public String PostSignIn(@RequestParam("mail") String mail, @RequestParam("password") String password, Model model) {
        System.out.println("mail: " + mail + " | password: " + password);
        model.addAttribute("msg", "Your new message");
        return "signin";
    }
}

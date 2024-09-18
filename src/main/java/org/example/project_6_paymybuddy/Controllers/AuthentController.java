package org.example.project_6_paymybuddy.Controllers;

import org.example.project_6_paymybuddy.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthentController {

    //private final UserService userService;

    @GetMapping("/login")
    public String login() {
        UserService.getUserName();
        return "login";
    }
}

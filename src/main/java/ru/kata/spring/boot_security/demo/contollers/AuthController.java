package ru.kata.spring.boot_security.demo.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @RequestMapping("/login")
    public String login() {
        return "/auth/login";
    }
}

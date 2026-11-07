package vitorlescola.aeps4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController{
        @GetMapping("/login")
        public String login() {
            return "login";
        }

        @GetMapping("/home")
        public String home() {
            return "home";
        }
}
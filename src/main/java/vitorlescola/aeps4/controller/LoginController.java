package vitorlescola.aeps4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController{
        @GetMapping("/login")
        public String login() {
            return "login";
        }

        @GetMapping("/menu")
        public String menu() {
            return "menu";
        }

    @GetMapping("/")
    public String vazio() {
        return "login";
    }
}
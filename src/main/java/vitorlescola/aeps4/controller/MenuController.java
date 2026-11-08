package vitorlescola.aeps4.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vitorlescola.aeps4.dto.CadastroDTO;
import vitorlescola.aeps4.service.CadastroService;

@Controller
public class MenuController{
    private final CadastroService cadastroService;

    public MenuController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @GetMapping("/menu")
    public String menu(Authentication authentication, Model model) {

        CadastroDTO conta = cadastroService.buscarConta(authentication.getName());

        model.addAttribute("conta", conta);

        return "menu";
    }

    @GetMapping("/usuario/compras")
    public String compras() {
        return "Compras";
    }
}
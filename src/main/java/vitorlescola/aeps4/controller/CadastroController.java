package vitorlescola.aeps4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vitorlescola.aeps4.service.CadastroService;

@Controller
public class CadastroController{
    private final CadastroService cadastroService;

    public CadastroController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    // Abre a tela de cadastro
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    // Recebe os dados do formulário
    @PostMapping("/cadastro")
    public String salvarCadastro(
            @RequestParam String nomePessoa,
            @RequestParam String cpf,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String tipoConta,
            RedirectAttributes redirectAttributes) {

        try {

            cadastroService.cadastrar(
                    nomePessoa,
                    cpf,
                    email,
                    senha,
                    tipoConta
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Conta criada com sucesso! Faça seu login."
            );

            return "redirect:/login";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/cadastro";
        }
    }
}
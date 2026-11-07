package vitorlescola.aeps4.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vitorlescola.aeps4.dto.CadastroDTO;
import vitorlescola.aeps4.service.CadastroService;

@Controller
public class ConfiguracaoController{
    private final CadastroService cadastroService;

    private final SecurityContextLogoutHandler logoutHandler =
            new SecurityContextLogoutHandler();

    public ConfiguracaoController(
            CadastroService cadastroService) {

        this.cadastroService = cadastroService;
    }

    // Página de configurações
    @GetMapping("/configuracao")
    public String configuracao(
            Authentication authentication,
            Model model) {

        CadastroDTO conta = cadastroService.buscarConta(
                authentication.getName()
        );

        model.addAttribute("conta", conta);

        return "configuracao";
    }

    // Página de edição
    @GetMapping("/configuracao/editar")
    public String editar(
            Authentication authentication,
            Model model) {

        CadastroDTO conta = cadastroService.buscarConta(
                authentication.getName()
        );

        model.addAttribute("conta", conta);

        return "EditarConta";
    }

    // Salvar alterações
    @PostMapping("/configuracao/editar")
    public String salvarEdicao(
            @ModelAttribute CadastroDTO dto,
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        try {

            cadastroService.editarConta(
                    authentication.getName(),
                    dto
            );

            // Encerra a sessão para autenticar
            // novamente com os dados atualizados.
            logoutHandler.logout(
                    request,
                    response,
                    authentication
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Dados atualizados! Faça login novamente."
            );

            return "redirect:/login";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/configuracao/editar";
        }
    }

    // Excluir conta
    @PostMapping("/configuracao/excluir")
    public String excluirConta(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {

        try {

            cadastroService.excluirConta(
                    authentication.getName()
            );

            // Encerra a sessão
            logoutHandler.logout(
                    request,
                    response,
                    authentication
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Sua conta foi excluída com sucesso!"
            );

            return "redirect:/login";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/configuracao";
        }
    }
}
package vitorlescola.aeps4.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vitorlescola.aeps4.dto.ProdutoDTO;
import vitorlescola.aeps4.model.Produto;
import vitorlescola.aeps4.service.ProdutoService;

@Controller
@RequestMapping("/fornecedor/produtos")
public class ProdutoController{
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Abrir página de cadastro
    @GetMapping("/cadastrar")
    public String cadastroProduto() {
        return "CadastroProduto";
    }

    // Salvar produto
    @PostMapping("/cadastrar")
    public String salvarProduto(
            @ModelAttribute ProdutoDTO dto,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {

            produtoService.cadastrarProduto(
                    dto,
                    authentication.getName()
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Produto cadastrado com sucesso!"
            );

            return "redirect:/fornecedor/produtos";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/fornecedor/produtos/cadastrar";
        }
    }

    // Listar produtos
    @GetMapping
    public String listarProdutos(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "produtos",
                produtoService.listarProdutos(
                        authentication.getName()
                )
        );

        return "ListaProdutos";
    }

    // Abrir página de edição
    @GetMapping("/editar/{id}")
    public String editarProduto(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        Produto produto = produtoService.buscarProduto(
                id,
                authentication.getName()
        );

        ProdutoDTO dto = new ProdutoDTO();

        dto.setNomeProduto(produto.getNomeProduto());
        dto.setEstoque(produto.getEstoque());

        model.addAttribute("produto", dto);
        model.addAttribute("id", id);

        return "EditarProduto";
    }

    // Salvar edição
    @PostMapping("/editar/{id}")
    public String salvarEdicao(
            @PathVariable Long id,
            @ModelAttribute ProdutoDTO dto,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {

            produtoService.editarProduto(
                    id,
                    dto,
                    authentication.getName()
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Produto atualizado com sucesso!"
            );

            return "redirect:/fornecedor/produtos";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/fornecedor/produtos/editar/" + id;
        }
    }

    // Excluir produto
    @PostMapping("/excluir/{id}")
    public String excluirProduto(
            @PathVariable Long id,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {

            produtoService.excluirProduto(
                    id,
                    authentication.getName()
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Produto excluído com sucesso!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/fornecedor/produtos";
    }
}
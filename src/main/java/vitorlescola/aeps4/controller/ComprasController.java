package vitorlescola.aeps4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vitorlescola.aeps4.dto.CompraDTO;
import vitorlescola.aeps4.model.Produto;
import vitorlescola.aeps4.service.ProdutoService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuario/compras")
public class ComprasController{
    private final ProdutoService produtoService;

    public ComprasController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Abrir página de compras
    @GetMapping
    public String compras(Model model) {

        List<Produto> produtos =
                produtoService.listarTodosProdutos();

        model.addAttribute("produtos", produtos);

        return "Compras";
    }

    // Realizar compra sem sair da página
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> comprar(
            @PathVariable Long id,
            @RequestBody CompraDTO dto) {

        try {

            int estoqueAtualizado =
                    produtoService.comprarProduto(
                            id,
                            dto.getQuantidade()
                    );

            return ResponseEntity.ok(
                    Map.of(
                            "sucesso", true,
                            "mensagem", "Compra realizada com sucesso!",
                            "estoque", estoqueAtualizado
                    )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "sucesso", false,
                            "mensagem", e.getMessage()
                    )
            );
        }
    }
}
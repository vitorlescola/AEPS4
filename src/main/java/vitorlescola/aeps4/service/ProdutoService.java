package vitorlescola.aeps4.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitorlescola.aeps4.dto.ProdutoDTO;
import vitorlescola.aeps4.model.Fornecedor;
import vitorlescola.aeps4.model.Produto;
import vitorlescola.aeps4.repository.FornecedorRepository;
import vitorlescola.aeps4.repository.ProdutoRepository;

import java.util.List;

@Service
public class ProdutoService{
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            FornecedorRepository fornecedorRepository) {

        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    // Cadastrar produto
    @Transactional
    public void cadastrarProduto(
            ProdutoDTO dto,
            String emailFornecedor) {

        validarProduto(dto);

        Fornecedor fornecedor = fornecedorRepository
                .findByEmail(emailFornecedor)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Fornecedor não encontrado!"
                        )
                );

        if (produtoRepository.existsByNomeProduto(
                dto.getNomeProduto())) {

            throw new IllegalArgumentException(
                    "Já existe um produto com esse nome!"
            );
        }

        Produto produto = new Produto();

        produto.setNomeProduto(dto.getNomeProduto());
        produto.setEstoque(dto.getEstoque());
        produto.setFornecedor(fornecedor);

        produtoRepository.save(produto);
    }

    // Listar produtos do fornecedor
    @Transactional(readOnly = true)
    public List<Produto> listarProdutos(String emailFornecedor) {

        return produtoRepository.findByFornecedorEmail(
                emailFornecedor
        );
    }

    // Buscar produto específico
    @Transactional(readOnly = true)
    public Produto buscarProduto(
            Long id,
            String emailFornecedor) {

        return produtoRepository
                .findByIdAndFornecedorEmail(
                        id,
                        emailFornecedor
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Produto não encontrado!"
                        )
                );
    }

    // Editar produto
    @Transactional
    public void editarProduto(
            Long id,
            ProdutoDTO dto,
            String emailFornecedor) {

        validarProduto(dto);

        Produto produto = buscarProduto(
                id,
                emailFornecedor
        );

        boolean nomeAlterado =
                !produto.getNomeProduto().equals(
                        dto.getNomeProduto()
                );

        if (nomeAlterado &&
                produtoRepository.existsByNomeProduto(
                        dto.getNomeProduto())) {

            throw new IllegalArgumentException(
                    "Já existe um produto com esse nome!"
            );
        }

        produto.setNomeProduto(dto.getNomeProduto());
        produto.setEstoque(dto.getEstoque());

        produtoRepository.save(produto);
    }

    // Excluir produto
    @Transactional
    public void excluirProduto(
            Long id,
            String emailFornecedor) {

        Produto produto = buscarProduto(
                id,
                emailFornecedor
        );

        produtoRepository.delete(produto);
    }

    // Validação dos dados
    private void validarProduto(ProdutoDTO dto) {

        if (dto.getNomeProduto() == null ||
                dto.getNomeProduto().isBlank()) {

            throw new IllegalArgumentException(
                    "O nome do produto é obrigatório!"
            );
        }

        if (dto.getEstoque() < 0) {

            throw new IllegalArgumentException(
                    "O estoque não pode ser negativo!"
            );
        }
    }
}
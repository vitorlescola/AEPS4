package vitorlescola.aeps4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vitorlescola.aeps4.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    List<Produto> findByFornecedorEmail(String email);

    Optional<Produto> findByIdAndFornecedorEmail(
            Long id,
            String email
    );

    boolean existsByNomeProduto(String nomeProduto);

    // Buscar todos os produtos e seus fornecedores
    @Query("""
        SELECT p
        FROM Produto p
        JOIN FETCH p.fornecedor
        ORDER BY p.nomeProduto
    """)
    List<Produto> listarTodosComFornecedor();

    // Atualizar estoque de maneira atômica
    @Modifying
    @Query("""
        UPDATE Produto p
        SET p.estoque = p.estoque - :quantidade
        WHERE p.id = :id
        AND p.estoque >= :quantidade
    """)
    int diminuirEstoque(
            @Param("id") Long id,
            @Param("quantidade") int quantidade
    );
}
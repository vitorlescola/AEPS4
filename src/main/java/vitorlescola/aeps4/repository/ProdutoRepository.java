package vitorlescola.aeps4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorlescola.aeps4.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    List<Produto> findByFornecedorEmail(String email);
    Optional<Produto> findByIdAndFornecedorEmail(Long id, String email);
    boolean existsByNomeProduto(String nomeProduto);
}
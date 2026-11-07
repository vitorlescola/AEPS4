package vitorlescola.aeps4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorlescola.aeps4.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long>{

}
package vitorlescola.aeps4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorlescola.aeps4.model.Fornecedor;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<Fornecedor,Long>{
    Optional<Fornecedor> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
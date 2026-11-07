package vitorlescola.aeps4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorlescola.aeps4.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
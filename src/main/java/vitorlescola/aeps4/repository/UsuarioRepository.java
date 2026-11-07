package vitorlescola.aeps4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitorlescola.aeps4.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    //Usuario findByNomeUsuario(String nomeUsuario);
}
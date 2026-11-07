package vitorlescola.aeps4.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vitorlescola.aeps4.model.Fornecedor;
import vitorlescola.aeps4.model.Usuario;
import vitorlescola.aeps4.repository.FornecedorRepository;
import vitorlescola.aeps4.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;

    public CustomUserDetailsService(
            UsuarioRepository usuarioRepository,
            FornecedorRepository fornecedorRepository) {

        this.usuarioRepository = usuarioRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElse(null);

        Fornecedor fornecedor = fornecedorRepository.findByEmail(email)
                .orElse(null);

        // Não permite contas ambíguas
        if (usuario != null && fornecedor != null) {
            throw new UsernameNotFoundException(
                    "Conta duplicada!"
            );
        }

        // Verifica se é usuário
        if (usuario != null) {

            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getSenha())
                    .roles("USUARIO")
                    .build();
        }

        // Verifica se é fornecedor
        if (fornecedor != null) {

            return User.builder()
                    .username(fornecedor.getEmail())
                    .password(fornecedor.getSenha())
                    .roles("FORNECEDOR")
                    .build();
        }

        throw new UsernameNotFoundException(
                "Usuário não encontrado!"
        );
    }
}
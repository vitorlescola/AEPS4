package vitorlescola.aeps4.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vitorlescola.aeps4.model.Fornecedor;
import vitorlescola.aeps4.model.Usuario;
import vitorlescola.aeps4.repository.FornecedorRepository;
import vitorlescola.aeps4.repository.UsuarioRepository;

@Service
public class CadastroService{
    private final UsuarioRepository usuarioRepository;
    private final FornecedorRepository fornecedorRepository;
    private final PasswordEncoder passwordEncoder;

    public CadastroService(
            UsuarioRepository usuarioRepository,
            FornecedorRepository fornecedorRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void cadastrar(
            String nomePessoa,
            String cpf,
            String email,
            String senha,
            String tipoConta) {

        // Verifica se o CPF já existe
        if (usuarioRepository.existsByCpf(cpf) ||
                fornecedorRepository.existsByCpf(cpf)) {

            throw new IllegalArgumentException(
                    "Este CPF já está cadastrado!"
            );
        }

        // Verifica se o e-mail já existe
        if (usuarioRepository.existsByEmail(email) ||
                fornecedorRepository.existsByEmail(email)) {

            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado!"
            );
        }

        // Criptografa a senha
        String senhaCriptografada =
                passwordEncoder.encode(senha);

        // Verifica o tipo de conta
        if ("USUARIO".equals(tipoConta)) {

            Usuario usuario = new Usuario();

            usuario.setNomePessoa(nomePessoa);
            usuario.setCpf(cpf);
            usuario.setEmail(email);
            usuario.setSenha(senhaCriptografada);

            usuarioRepository.save(usuario);

        } else if ("FORNECEDOR".equals(tipoConta)) {

            Fornecedor fornecedor = new Fornecedor();

            fornecedor.setNomePessoa(nomePessoa);
            fornecedor.setCpf(cpf);
            fornecedor.setEmail(email);
            fornecedor.setSenha(senhaCriptografada);

            fornecedorRepository.save(fornecedor);

        } else {

            throw new IllegalArgumentException(
                    "Tipo de conta inválido!"
            );
        }
    }
}
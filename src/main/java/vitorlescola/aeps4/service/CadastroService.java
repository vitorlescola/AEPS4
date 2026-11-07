package vitorlescola.aeps4.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vitorlescola.aeps4.dto.CadastroDTO;
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

    // Cadastro
    @Transactional
    public void cadastrar(
            String nomePessoa,
            String cpf,
            String email,
            String senha,
            String tipoConta) {

        validarDados(cpf, email, null);

        String senhaCriptografada =
                passwordEncoder.encode(senha);

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

    // Busca os dados da pessoa autenticada
    @Transactional
    public CadastroDTO buscarConta(String email) {

        Usuario usuario = usuarioRepository
                .findByEmail(email).orElse(null);

        Fornecedor fornecedor = fornecedorRepository
                .findByEmail(email).orElse(null);

        if (usuario != null && fornecedor != null) {
            throw new IllegalStateException(
                    "Foram encontradas contas duplicadas."
            );
        }

        CadastroDTO dto = new CadastroDTO();

        if (usuario != null) {

            dto.setNomePessoa(usuario.getNomePessoa());
            dto.setCpf(usuario.getCpf());
            dto.setEmail(usuario.getEmail());
            dto.setTipoConta("USUARIO");

            return dto;
        }

        if (fornecedor != null) {

            dto.setNomePessoa(fornecedor.getNomePessoa());
            dto.setCpf(fornecedor.getCpf());
            dto.setEmail(fornecedor.getEmail());
            dto.setTipoConta("FORNECEDOR");

            return dto;
        }

        throw new IllegalArgumentException(
                "Conta não encontrada!"
        );
    }

    // Atualiza a conta
    @Transactional
    public void editarConta(
            String emailAtual,
            CadastroDTO dto) {

        validarDados(
                dto.getCpf(),
                dto.getEmail(),
                emailAtual
        );

        Usuario usuario = usuarioRepository
                .findByEmail(emailAtual).orElse(null);

        if (usuario != null) {

            usuario.setNomePessoa(dto.getNomePessoa());
            usuario.setCpf(dto.getCpf());
            usuario.setEmail(dto.getEmail());

            if (dto.getSenha() != null &&
                    !dto.getSenha().isBlank()) {

                usuario.setSenha(
                        passwordEncoder.encode(dto.getSenha())
                );
            }

            usuarioRepository.save(usuario);
            return;
        }

        Fornecedor fornecedor = fornecedorRepository
                .findByEmail(emailAtual).orElse(null);

        if (fornecedor != null) {

            fornecedor.setNomePessoa(dto.getNomePessoa());
            fornecedor.setCpf(dto.getCpf());
            fornecedor.setEmail(dto.getEmail());

            if (dto.getSenha() != null &&
                    !dto.getSenha().isBlank()) {

                fornecedor.setSenha(
                        passwordEncoder.encode(dto.getSenha())
                );
            }

            fornecedorRepository.save(fornecedor);
            return;
        }

        throw new IllegalArgumentException(
                "Conta não encontrada!"
        );
    }

    // Exclui a conta
    @Transactional
    public void excluirConta(String email) {

        Usuario usuario = usuarioRepository
                .findByEmail(email).orElse(null);

        if (usuario != null) {
            usuarioRepository.delete(usuario);
            return;
        }

        Fornecedor fornecedor = fornecedorRepository
                .findByEmail(email).orElse(null);

        if (fornecedor != null) {
            fornecedorRepository.delete(fornecedor);
            return;
        }

        throw new IllegalArgumentException(
                "Conta não encontrada!"
        );
    }

    // Valida CPF e e-mail
    private void validarDados(
            String cpf,
            String email,
            String emailAtual) {

        boolean emailDuplicado =
                usuarioRepository.existsByEmail(email) ||
                        fornecedorRepository.existsByEmail(email);

        boolean cpfDuplicado =
                usuarioRepository.existsByCpf(cpf) ||
                        fornecedorRepository.existsByCpf(cpf);

        if (emailDuplicado &&
                !email.equals(emailAtual)) {

            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado!"
            );
        }

        if (cpfDuplicado) {

            boolean mesmoCpf =
                    cpfPertenceAoEmail(cpf, emailAtual);

            if (!mesmoCpf) {
                throw new IllegalArgumentException(
                        "Este CPF já está cadastrado!"
                );
            }
        }
    }

    private boolean cpfPertenceAoEmail(
            String cpf,
            String email) {

        if (email == null) {
            return false;
        }

        Usuario usuario = usuarioRepository
                .findByEmail(email).orElse(null);

        if (usuario != null) {
            return usuario.getCpf().equals(cpf);
        }

        Fornecedor fornecedor = fornecedorRepository
                .findByEmail(email).orElse(null);

        return fornecedor != null &&
                fornecedor.getCpf().equals(cpf);
    }
}
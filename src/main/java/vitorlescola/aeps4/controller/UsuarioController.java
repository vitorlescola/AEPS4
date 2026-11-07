package vitorlescola.aeps4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vitorlescola.aeps4.model.Usuario;
import vitorlescola.aeps4.repository.UsuarioRepository;

import java.util.List;

@RestController
public class UsuarioController{/*

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoderSenha;

    @GetMapping
    public List<Usuario> usuarioList(){
        return usuarioRepository.findAll();
    }

    @PostMapping
    public String criarConta(Model module){
        module.addAttribute("usuario", new Usuario());
        return "criarConta";
    }

    @PostMapping("/criarConta")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model modelo) {
        if (usuarioRepository.findByNomeUsuario(usuario.getNomePessoa()) != null) {
            modelo.addAttribute("mensagem", "Nome de usuário já existe!");
            return "registrar";
        }
        usuario.setSenha(encoderSenha.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

    @DeleteMapping("/{id}")
    public void deletarConta(@PathVariable Long id){
        usuarioRepository.deleteById(id);
    }

    @PostMapping("{/id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario newUsuario){
        Usuario usuario=usuarioRepository.findById(id).get();
        usuario.setNomePessoa(newUsuario.getNomePessoa());
        usuario.setEmail(newUsuario.getEmail());
        usuario.setSenha(newUsuario.getSenha());

        return usuarioRepository.save(usuario);
    }*/
}
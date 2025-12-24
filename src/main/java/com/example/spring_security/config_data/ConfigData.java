package com.example.spring_security.config_data;

import com.example.spring_security.domain.Permissao;
import com.example.spring_security.domain.Role;
import com.example.spring_security.domain.Usuario;
import com.example.spring_security.domain.UsuarioPermissao;
import com.example.spring_security.repository.PermissaoRepository;
import com.example.spring_security.repository.UsuarioPermissaoRepository;
import com.example.spring_security.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigData implements CommandLineRunner {

    private final PermissaoRepository permissaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioPermissaoRepository usuarioPermissaoRepository;

    public ConfigData(PermissaoRepository permissaoRepository, UsuarioRepository usuarioRepository, UsuarioPermissaoRepository usuarioPermissaoRepository) {
        this.permissaoRepository = permissaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioPermissaoRepository = usuarioPermissaoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (Role role : Role.values()){
            if (!permissaoRepository.existsByRole(role)){
                Permissao permissao = permissaoRepository.save(new Permissao(null, role));

                List<Usuario> usuarios = usuarioRepository.findAll();

                for (Usuario usuario: usuarios){
                    if (!usuarioPermissaoRepository.existsByUsuarioAndPermissao(usuario, permissao)){
                        usuarioPermissaoRepository.save(
                                new UsuarioPermissao(null, usuario, permissao, false)
                        );
                    }
                }
            }
        }
    }
}

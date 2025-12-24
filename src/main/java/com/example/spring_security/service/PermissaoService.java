package com.example.spring_security.service;

import com.example.spring_security.domain.Permissao;
import com.example.spring_security.domain.Role;
import com.example.spring_security.domain.Usuario;
import com.example.spring_security.domain.UsuarioPermissao;
import com.example.spring_security.dto.PermissaoDTO;
import com.example.spring_security.dto.PermissoesUsuarioDTO;
import com.example.spring_security.repository.PermissaoRepository;
import com.example.spring_security.repository.UsuarioPermissaoRepository;
import com.example.spring_security.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioPermissaoRepository usuarioPermissaoRepository;

    public PermissaoService(PermissaoRepository permissaoRepository, UsuarioRepository usuarioRepository, UsuarioPermissaoRepository usuarioPermissaoRepository) {
        this.permissaoRepository = permissaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioPermissaoRepository = usuarioPermissaoRepository;
    }

    public void salvarPermissoes(PermissoesUsuarioDTO permissoesUsuarioDTO){

        Usuario usuario = usuarioRepository
                .findById(permissoesUsuarioDTO.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        if (usuario.listaPermissoes().contains("ADMIN")
                && (!SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
                .equals(usuario.getUsername()))){
            throw new IllegalArgumentException("voce nao pode alterar as roles de outro usuario ADMIN");
        }

        List<Permissao> permissaoList = permissaoRepository.findAll();


        for (Permissao permissao: permissaoList){
            int contador = 0;
            for (PermissaoDTO permissaoDTO: permissoesUsuarioDTO.listaRoles()){

                if (permissaoDTO.role().equals(permissao.getRole())){
                    contador += 1;
                }
            }

            if (contador >= 2){
                throw new IllegalArgumentException("Permissao repetida na lista");
            }
        }

        for (PermissaoDTO permissaoDTO: permissoesUsuarioDTO.listaRoles()){

            Permissao permissao = permissaoList
                    .stream()
                    .filter(permissao1 -> permissao1.getRole().equals(permissaoDTO.role()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Permissao nao encontrada"));

            UsuarioPermissao usuarioPermissao = usuarioPermissaoRepository.findByUsuarioAndPermissao(usuario, permissao).orElseThrow(() -> new IllegalArgumentException("Relacao nao encontrada"));

            usuarioPermissao.setAutorizado(permissaoDTO.autorizado());

            usuarioPermissaoRepository.save(usuarioPermissao);

        }

    }
}

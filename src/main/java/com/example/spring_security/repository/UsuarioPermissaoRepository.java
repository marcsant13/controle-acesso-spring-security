package com.example.spring_security.repository;

import com.example.spring_security.domain.Permissao;
import com.example.spring_security.domain.Usuario;
import com.example.spring_security.domain.UsuarioPermissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioPermissaoRepository extends JpaRepository<UsuarioPermissao, Long> {
    boolean existsByUsuarioAndPermissao(Usuario usuario, Permissao permissao);

    Optional<UsuarioPermissao> findByUsuarioAndPermissao(Usuario usuario, Permissao permissao);
}

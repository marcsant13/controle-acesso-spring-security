package com.example.spring_security.repository;

import com.example.spring_security.domain.Permissao;
import com.example.spring_security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    boolean existsByRole(Role role);

    Optional<Permissao> findByRole(Role role);
}

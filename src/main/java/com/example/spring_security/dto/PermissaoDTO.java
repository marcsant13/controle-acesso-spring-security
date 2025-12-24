package com.example.spring_security.dto;

import com.example.spring_security.domain.Role;

public record PermissaoDTO(
        Role role,
        boolean autorizado
) {
}

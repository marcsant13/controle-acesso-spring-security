package com.example.spring_security.dto;

import com.example.spring_security.domain.Role;

import java.util.List;
import java.util.Set;

public record PermissoesUsuarioDTO(
        Long idUsuario,
        List<PermissaoDTO> listaRoles
) {
}

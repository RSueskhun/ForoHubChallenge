package com.challenge.forohub.domain.usuarios.dto;

import com.challenge.forohub.domain.usuarios.Role;

public record ActualizarUsuarioDTO(
        String password,
        Role role,
        String nombre,
        String apellido,
        String email,
        Boolean enabled
) {
}
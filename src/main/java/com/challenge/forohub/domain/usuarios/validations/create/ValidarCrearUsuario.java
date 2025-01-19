package com.challenge.forohub.domain.usuarios.validations.create;

import com.challenge.forohub.domain.usuarios.dto.CrearUsuarioDTO;

public interface ValidarCrearUsuario {
    void validate(CrearUsuarioDTO data);
}
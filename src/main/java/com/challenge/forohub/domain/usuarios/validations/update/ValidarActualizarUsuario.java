package com.challenge.forohub.domain.usuarios.validations.update;

import com.challenge.forohub.domain.usuarios.dto.ActualizarUsuarioDTO;

public interface ValidarActualizarUsuario {
    void validate(ActualizarUsuarioDTO data);
}
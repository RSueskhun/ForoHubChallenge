package com.challenge.forohub.domain.topicos.validations.create;

import com.challenge.forohub.domain.topicos.dto.CrearTopicoDTO;
import com.challenge.forohub.domain.usuarios.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCreado {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CrearTopicoDTO data) {
        var existeUsuario = repository.existsById(data.usuarioId());
        if (!existeUsuario) {
            throw new ValidationException("ESTE USUARIO NO EXISTE");
        }

        var usuarioHabilitado = repository.findById(data.usuarioId()).get().getEnabled();
        if (!usuarioHabilitado) {
            throw new ValidationException("ESTE USUARIO NO ESTA HABILITADO");
        }

    }
}

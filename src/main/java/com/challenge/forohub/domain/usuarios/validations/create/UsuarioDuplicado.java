package com.challenge.forohub.domain.usuarios.validations.create;

import com.challenge.forohub.domain.usuarios.dto.CrearUsuarioDTO;
import com.challenge.forohub.domain.usuarios.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDuplicado implements ValidarCrearUsuario{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CrearUsuarioDTO data) {
        var usuarioDuplicado = repository.findByUsername(data.username());
        if(usuarioDuplicado != null){
            throw new ValidationException("ESTE USUARIO YA EXISTE");
        }

        var emailDuplicado = repository.findByEmail(data.email());
        if(emailDuplicado != null){
            throw new ValidationException("ESTE EMAIL YA EXISTE");
        }
    }
}
package com.challenge.forohub.domain.usuarios.validations.update;

import com.challenge.forohub.domain.usuarios.dto.ActualizarUsuarioDTO;
import com.challenge.forohub.domain.usuarios.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class validaActualizacionUsuario implements ValidarActualizarUsuario{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(ActualizarUsuarioDTO data) {
        if(data.email() != null){
            var emailDuplicado = repository.findByEmail(data.email());
            if(emailDuplicado != null){
                throw new ValidationException("ESTE EMAIL YA ESTA EN USO");
            }
        }
    }
}
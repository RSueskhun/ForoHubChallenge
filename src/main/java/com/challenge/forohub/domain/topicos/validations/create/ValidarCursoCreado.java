package com.challenge.forohub.domain.topicos.validations.create;

import com.challenge.forohub.domain.curso.repository.CursoRepository;
import com.challenge.forohub.domain.topicos.dto.CrearTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoCreado implements ValidarTopicoCreado{

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(CrearTopicoDTO data) {
        var ExisteCurso = repository.existsById(data.cursoId());
        if(!ExisteCurso){
            throw new ValidationException("ESTE CURSO NO EXISTE EN EL DIRECTORIO");
        }

        var cursoHabilitado = repository.findById(data.cursoId()).get().getActivo();
        if(!cursoHabilitado){
            throw new ValidationException("EL CURSO NO ESTA HABILITADO O DISPONIBLE");
        }
    }
}
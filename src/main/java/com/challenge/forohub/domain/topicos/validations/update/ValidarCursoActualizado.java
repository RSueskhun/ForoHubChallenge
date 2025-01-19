package com.challenge.forohub.domain.topicos.validations.update;

import com.challenge.forohub.domain.curso.repository.CursoRepository;
import com.challenge.forohub.domain.topicos.dto.ActualizarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoActualizado implements ValidarTopicoActualizado{

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(ActualizarTopicoDTO data) {
        if(data.cursoId() != null){
            var ExisteCurso = repository.existsById(data.cursoId());
            if (!ExisteCurso){
                throw new ValidationException("ESTE CURSO NO EXISTE EN EL DIRECTORIO");
            }

            var cursoHabilitado = repository.findById(data.cursoId()).get().getActivo();
            if(!cursoHabilitado){
                throw new ValidationException("ESTE CURSO NO ESTA HABILITADO O DISPONIBLE");
            }
        }

    }
}
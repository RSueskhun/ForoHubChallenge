package com.challenge.forohub.domain.respuesta.validations.create;


import com.challenge.forohub.domain.respuesta.dto.CrearRespuestaDTO;
import com.challenge.forohub.domain.topicos.Estado;
import com.challenge.forohub.domain.topicos.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaTopicoValida implements ValidarRespuestaCreada {

    @Autowired
    private TopicoRepository repository;

    @Override
    public void validate(CrearRespuestaDTO data) {
        var topicoExiste = repository.existsById(data.topicoId());

        if (!topicoExiste){
            throw new ValidationException("ESTE TOPICO NO EXISTE");
        }

        var topicoAbierto = repository.findById(data.topicoId()).get().getEstado();

        if(topicoAbierto != Estado.OPEN){
            throw new ValidationException("ESTE TOPICO NO ESTA ABIERTO");
        }
    }
}
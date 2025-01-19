package com.challenge.forohub.domain.topicos.validations.create;

import com.challenge.forohub.domain.topicos.dto.CrearTopicoDTO;
import com.challenge.forohub.domain.topicos.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCreado{

    @Autowired
    private TopicoRepository topicoRepository;


    @Override
    public void validate(CrearTopicoDTO data) {
        var topicoDuplicado = topicoRepository.existsByTituloAndMensaje(data.titulo(), data.mensaje());
        if(topicoDuplicado){
            throw new ValidationException("ESTE TOPICO YA NO EXISTE" + topicoRepository.findByTitulo(data.titulo()).getId());

        }
    }
}
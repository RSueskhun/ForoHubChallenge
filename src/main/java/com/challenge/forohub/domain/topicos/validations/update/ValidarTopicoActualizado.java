package com.challenge.forohub.domain.topicos.validations.update;

import com.challenge.forohub.domain.topicos.dto.ActualizarTopicoDTO;

public interface ValidarTopicoActualizado {
    void validate(ActualizarTopicoDTO data);
}
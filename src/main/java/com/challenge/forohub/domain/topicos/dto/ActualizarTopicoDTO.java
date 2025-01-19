package com.challenge.forohub.domain.topicos.dto;

import com.challenge.forohub.domain.topicos.Estado;

public record ActualizarTopicoDTO(
        String titulo,
        String mensaje,
        Estado estado,
        Long cursoId
) {
}
package com.challenge.forohub.domain.Topico;

import java.time.LocalDate;

public record DatosListadoTopico(
        Long id,
        String autor,
        String titulo,
        String mensaje,
        String nombreCurso,
        LocalDate fechaDeCreacion) {

    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getAutor().getNombre(), topico.getTitulo(), topico.getMensaje(), topico.getCurso(), topico.getFechaDeCreacion());
    }
}

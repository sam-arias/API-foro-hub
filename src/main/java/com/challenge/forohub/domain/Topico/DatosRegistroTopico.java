package com.challenge.forohub.domain.Topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String nombreCurso,
        @NotNull
        Long idAutor
) {
}

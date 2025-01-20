package com.challenge.forohub.domain.usuarios;

public record DatosListadoUsuario(
        Long idUsuario,
        String nombre,
        String email) {

    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}

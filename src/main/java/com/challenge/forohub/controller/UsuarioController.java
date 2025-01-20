package com.challenge.forohub.controller;

import com.challenge.forohub.domain.usuarios.*;
import com.challenge.forohub.infra.security.AutenticacionService;
import com.challenge.forohub.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AutenticacionService autenticacionService;

/*
    @Autowired
    private AuthenticationManager authenticationManager;
*/

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuario>> listadoDeUsuarios(@PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findByEnabledTrue(paginacion).map(DatosListadoUsuario::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        // Obtener al usuario
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());

        // Actualizar el usuario con los nuevos datos
        usuario.actualizarUsuario(datosActualizarUsuario);

        // Regenerar el token JWT con el nuevo email
        String nuevoToken = tokenService.generarToken(usuario);

        // Retornar la respuesta con el nuevo token
        return ResponseEntity.ok(new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                nuevoToken // Incluimos el nuevo token en la respuesta
        ));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivarUsuario();
        return ResponseEntity.noContent().build();
    }
}



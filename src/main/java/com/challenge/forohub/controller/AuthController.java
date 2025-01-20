package com.challenge.forohub.controller;

import com.challenge.forohub.domain.usuarios.*;
import com.challenge.forohub.infra.security.AutenticacionService;
import com.challenge.forohub.infra.security.DatosJWTToken;
import com.challenge.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AutenticacionService autenticacionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registro de nuevo usuario
    @PostMapping("/register")
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        try {
            Usuario nuevoUsuario = autenticacionService.registrarUsuario(datosAutenticacionUsuario);
            String jwtToken = autenticacionService.generarToken(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(new DatosJWTToken(jwtToken));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Autenticación de usuario existente
    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        try {
            String jwtToken = autenticacionService.autenticarUsuario(datosAutenticacionUsuario);
            return ResponseEntity.ok(new DatosJWTToken(jwtToken));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}

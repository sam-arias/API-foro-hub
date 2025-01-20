package com.challenge.forohub.infra.security;

import com.challenge.forohub.domain.usuarios.DatosAutenticacionUsuario;
import com.challenge.forohub.domain.usuarios.Usuario;
import com.challenge.forohub.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService { // me dice que no implemento el metodo

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el PasswordEncoder

    @Autowired
    private TokenService tokenService; // Inyectamos el servicio de token

    // Método requerido por UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getClave())
                .roles("USER") // Puedes personalizar los roles según tu lógica
                .build();
    }

    // Método para registrar un nuevo usuario
    public Usuario registrarUsuario(DatosAutenticacionUsuario datosAutenticacionUsuario) {
        if (usuarioRepository.findByEmail(datosAutenticacionUsuario.email()) != null) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }

        Usuario nuevoUsuario = new Usuario(datosAutenticacionUsuario);
        String claveCodificada = passwordEncoder.encode(datosAutenticacionUsuario.clave());
        nuevoUsuario.setClave(claveCodificada);
        return usuarioRepository.save(nuevoUsuario);
    }

    // Método para autenticar a un usuario existente
    public String autenticarUsuario(DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(datosAutenticacionUsuario.email());
        if (usuario == null || !passwordEncoder.matches(datosAutenticacionUsuario.clave(), usuario.getClave())) {
            throw new IllegalArgumentException("Credenciales incorrectas.");
        }
        return tokenService.generarToken(usuario);
    }

    // Método para generar el token JWT después de registrar al usuario
    public String generarToken(Usuario usuario) {
        return tokenService.generarToken(usuario);
    }

}

/*// Método para cargar un usuario por email (para la autenticación)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

    // Método para registrar un nuevo usuario
    public Usuario registrarUsuario(DatosAutenticacionUsuario datosAutenticacionUsuario) {
        // Verificar si el email ya está registrado
        if (usuarioRepository.findByEmail(datosAutenticacionUsuario.email()) != null) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }

        // Crear un nuevo usuario con los datos recibidos
        Usuario nuevoUsuario = new Usuario(datosAutenticacionUsuario);

        // Codificar la contraseña antes de almacenarla
        String claveCodificada = passwordEncoder.encode(datosAutenticacionUsuario.clave());
        nuevoUsuario.setClave(claveCodificada);

        // Guardar el nuevo usuario en la base de datos
        return usuarioRepository.save(nuevoUsuario);
    }*/

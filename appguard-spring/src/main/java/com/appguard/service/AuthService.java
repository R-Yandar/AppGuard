package com.appguard.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appguard.model.Usuario;
import com.appguard.repository.UsuarioRepository;

/**
 * Servicio encargado de validar la autenticación
 * de los usuarios registrados en AppGuard.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Valida el documento, la contraseña y el estado del usuario.
     *
     * @param documento documento del usuario
     * @param contrasena contraseña ingresada
     * @return usuario autenticado si los datos son correctos
     */
    public Optional<Usuario> autenticar(String documento, String contrasena) {

        Optional<Usuario> usuarioEncontrado =
                usuarioRepository.findByDocumento(documento);

        if (usuarioEncontrado.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioEncontrado.get();

        if (!"Activo".equalsIgnoreCase(usuario.getEstado())) {
            return Optional.empty();
        }

        if (!passwordEncoder.matches(
                contrasena,
                usuario.getContrasena()
        )) {
            return Optional.empty();
        }

        return Optional.of(usuario);
    }

    /**
 * Activa la cuenta de un usuario y guarda la contraseña cifrada.
 *
 * @param documento documento del usuario
 * @param correo correo registrado
 * @param contrasena contraseña definida por el usuario
 * @return usuario activado si los datos son válidos
 */
public Optional<Usuario> activarCuenta(
        String documento,
        String correo,
        String contrasena) {

    Optional<Usuario> usuarioEncontrado =
            usuarioRepository.findByDocumento(documento);

    if (usuarioEncontrado.isEmpty()) {
        return Optional.empty();
    }

    Usuario usuario = usuarioEncontrado.get();

    if (usuario.getCorreo() == null ||
        !usuario.getCorreo().equalsIgnoreCase(correo)) {
        return Optional.empty();
    }

    String contrasenaCifrada =
            passwordEncoder.encode(contrasena);

    usuario.setContrasena(contrasenaCifrada);
    usuario.setEstado("Activo");

    usuarioRepository.save(usuario);

    return Optional.of(usuario);
}

}
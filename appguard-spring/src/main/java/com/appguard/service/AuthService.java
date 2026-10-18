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
/**
 * Activa la cuenta de un usuario validando documento,
 * correo y código de activación.
 *
 * @param documento documento del usuario
 * @param correo correo registrado
 * @param codigoActivacion código de seis dígitos asignado al usuario
 * @param contrasena contraseña definida por el usuario
 * @return usuario activado si los datos son válidos
 */
public Optional<Usuario> activarCuenta(
        String documento,
        String correo,
        String codigoActivacion,
        String contrasena) {

    Optional<Usuario> usuarioEncontrado =
            usuarioRepository.findByDocumento(documento);

    if (usuarioEncontrado.isEmpty()) {
        return Optional.empty();
    }

    Usuario usuario = usuarioEncontrado.get();

    // Evitar que una cuenta ya activada cambie su contraseña
    // utilizando nuevamente el formulario de activación.
    if (usuario.getContrasena() != null &&
        !usuario.getContrasena().isBlank()) {
        return Optional.empty();
    }

    // Validar que el correo ingresado corresponda al registrado.
    if (usuario.getCorreo() == null ||
        !usuario.getCorreo().equalsIgnoreCase(correo)) {
        return Optional.empty();
    }

    // Validar el código de activación asignado al usuario.
    if (usuario.getCodigoActivacion() == null ||
        !usuario.getCodigoActivacion().equals(codigoActivacion)) {
        return Optional.empty();
    }

    // Cifrar la contraseña antes de guardarla.
    String contrasenaCifrada =
            passwordEncoder.encode(contrasena);

    usuario.setContrasena(contrasenaCifrada);
    usuario.setEstado("Activo");

    // El código deja de ser válido después de activar la cuenta.
    usuario.setCodigoActivacion(null);

    usuarioRepository.save(usuario);

    return Optional.of(usuario);
}

}
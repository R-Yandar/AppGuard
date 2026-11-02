package com.appguard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appguard.model.Usuario;
import com.appguard.repository.UsuarioRepository;
import com.appguard.service.AuthService;

/**
 * Pruebas unitarias para los procesos de autenticación
 * y activación de usuarios de AppGuard.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private AuthService authService;

    private Usuario usuario;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void configurar() {

        authService = new AuthService(usuarioRepository);

        passwordEncoder = new BCryptPasswordEncoder();

        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setIdRol(3);
        usuario.setNombre("Daniel Alejandro");
        usuario.setApellido("Muñoz Ruiz");
        usuario.setDocumento("1056789012");
        usuario.setCorreo("daniel.prueba@appguard.com");
        usuario.setTelefono("3105678912");
    }

    /**
     * Verifica que un usuario activo pueda autenticarse
     * cuando la contraseña ingresada es correcta.
     */
    @Test
    void autenticarDebeRetornarUsuarioConCredencialesCorrectas() {

        usuario.setEstado("Activo");
        usuario.setContrasena(
                passwordEncoder.encode("AppGuard123")
        );

        when(
            usuarioRepository.findByDocumento("1056789012")
        ).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado =
                authService.autenticar(
                        "1056789012",
                        "AppGuard123"
                );

        assertTrue(resultado.isPresent());
        assertEquals(
                "Daniel Alejandro",
                resultado.get().getNombre()
        );

        verify(usuarioRepository)
                .findByDocumento("1056789012");
    }

    /**
     * Verifica que la autenticación sea rechazada
     * cuando la contraseña es incorrecta.
     */
    @Test
    void autenticarDebeRechazarContrasenaIncorrecta() {

        usuario.setEstado("Activo");
        usuario.setContrasena(
                passwordEncoder.encode("AppGuard123")
        );

        when(
            usuarioRepository.findByDocumento("1056789012")
        ).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado =
                authService.autenticar(
                        "1056789012",
                        "ClaveIncorrecta"
                );

        assertFalse(resultado.isPresent());
    }

    /**
     * Verifica que una cuenta pueda activarse
     * cuando los datos y el código son correctos.
     */
    @Test
    void activarCuentaDebeActivarUsuarioCorrectamente() {

        usuario.setEstado("Inactivo");
        usuario.setCodigoActivacion("123456");
        usuario.setContrasena(null);

        when(
            usuarioRepository.findByDocumento("1056789012")
        ).thenReturn(Optional.of(usuario));

        when(
            usuarioRepository.save(any(Usuario.class))
        ).thenAnswer(invocacion ->
                invocacion.getArgument(0));

        Optional<Usuario> resultado =
                authService.activarCuenta(
                        "1056789012",
                        "daniel.prueba@appguard.com",
                        "123456",
                        "AppGuard123"
                );

        assertTrue(resultado.isPresent());

        Usuario usuarioActivado = resultado.get();

        assertEquals(
                "Activo",
                usuarioActivado.getEstado()
        );

        assertNull(
                usuarioActivado.getCodigoActivacion()
        );

        assertNotNull(
                usuarioActivado.getContrasena()
        );

        assertTrue(
                passwordEncoder.matches(
                        "AppGuard123",
                        usuarioActivado.getContrasena()
                )
        );

        verify(usuarioRepository)
                .save(any(Usuario.class));
    }
}
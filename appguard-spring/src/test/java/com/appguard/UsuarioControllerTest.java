package com.appguard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.appguard.controller.UsuarioController;
import com.appguard.model.Usuario;
import com.appguard.repository.UsuarioRepository;

/**
 * Pruebas unitarias para el controlador de usuarios de AppGuard.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void configurarUsuario() {

        usuario = new Usuario();

        usuario.setIdUsuario(1);
        usuario.setIdRol(3);
        usuario.setNombre("Daniel Alejandro");
        usuario.setApellido("Muñoz Ruiz");
        usuario.setDocumento("1056789012");
        usuario.setCorreo("daniel.prueba@appguard.com");
        usuario.setTelefono("3105678912");
        usuario.setEstado("Activo");
    }

    /**
     * Verifica que el controlador consulte correctamente
     * los usuarios registrados.
     */
    @Test
    void listarUsuariosDebeRetornarUsuarios() {

        when(usuarioRepository.findAll())
                .thenReturn(List.of(usuario));

        List<Usuario> resultado =
                usuarioController.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(
                "Daniel Alejandro",
                resultado.get(0).getNombre()
        );

        verify(usuarioRepository).findAll();
    }

    /**
     * Verifica que al registrar un usuario se genere
     * un código de activación y se asigne estado Inactivo.
     */
    @Test
    void crearUsuarioDebeGenerarCodigoYEstadoInactivo() {

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocacion ->
                        invocacion.getArgument(0));

        Usuario resultado =
                usuarioController.crearUsuario(usuario);

        assertNotNull(resultado);

        assertEquals(
                "Inactivo",
                resultado.getEstado()
        );

        assertNotNull(
                resultado.getCodigoActivacion()
        );

        assertEquals(
                6,
                resultado.getCodigoActivacion().length()
        );

        assertTrue(
                resultado.getCodigoActivacion()
                        .matches("\\d{6}")
        );

        verify(usuarioRepository)
                .save(any(Usuario.class));
    }
}
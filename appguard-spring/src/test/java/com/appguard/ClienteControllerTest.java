package com.appguard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.appguard.controller.ClienteController;
import com.appguard.model.Cliente;
import com.appguard.repository.ClienteRepository;
import com.appguard.repository.PuestoServicioRepository;

class ClienteControllerTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PuestoServicioRepository puestoRepository;

    private ClienteController clienteController;

    @BeforeEach
    void configurar() {
        MockitoAnnotations.openMocks(this);

        clienteController = new ClienteController(
                clienteRepository,
                puestoRepository
        );
    }

    @Test
    void rechazaClienteSinNombre() {

        Cliente cliente = new Cliente();

        cliente.setNit("900123456");
        cliente.setDireccion("Calle 10 #20-30");
        cliente.setTelefono("3001234567");
        cliente.setCorreo("cliente@appguard.com");

        ResponseEntity<?> respuesta =
                clienteController.crearCliente(cliente);

        assertEquals(
                400,
                respuesta.getStatusCode().value()
        );

        assertEquals(
                "El nombre del cliente es obligatorio.",
                respuesta.getBody()
        );
    }

    @Test
    void asignaEstadoActivoAlCrearCliente() {

        Cliente cliente = new Cliente();

        cliente.setNombreCliente(
                "Cliente Prueba AppGuard"
        );

        cliente.setNit("900123456");

        cliente.setDireccion(
                "Calle 10 #20-30"
        );

        cliente.setTelefono(
                "3001234567"
        );

        cliente.setCorreo(
                "cliente@appguard.com"
        );

        cliente.setEstado(null);

        when(
                clienteRepository.save(
                        any(Cliente.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        ResponseEntity<?> respuesta =
                clienteController.crearCliente(cliente);

        assertEquals(
                200,
                respuesta.getStatusCode().value()
        );

        Cliente clienteGuardado =
                (Cliente) respuesta.getBody();

        assertNotNull(clienteGuardado);

        assertEquals(
                "Activo",
                clienteGuardado.getEstado()
        );

        verify(
                clienteRepository
        ).save(
                any(Cliente.class)
        );
    }
}
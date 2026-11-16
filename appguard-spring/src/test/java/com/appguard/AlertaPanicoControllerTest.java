package com.appguard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.appguard.controller.AlertaPanicoController;
import com.appguard.model.AlertaPanico;
import com.appguard.repository.AlertaPanicoRepository;
import com.appguard.repository.GuardaRepository;
import com.appguard.repository.PuestoServicioRepository;
import com.appguard.repository.TurnoRepository;
import com.appguard.repository.UsuarioRepository;

class AlertaPanicoControllerTest {

    @Mock
    private AlertaPanicoRepository alertaRepository;

    @Mock
    private GuardaRepository guardaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private PuestoServicioRepository puestoRepository;

    private AlertaPanicoController alertaController;

    @BeforeEach
    void configurar() {

        MockitoAnnotations.openMocks(this);

        alertaController =
                new AlertaPanicoController(
                        alertaRepository,
                        guardaRepository,
                        usuarioRepository,
                        turnoRepository,
                        puestoRepository
                );
    }

    @Test
    void activaAlertaSinTurnoConUbicacionPorDefecto() {

        when(
                turnoRepository.findByIdGuardaAndFecha(
                        eq(1),
                        any(LocalDate.class)
                )
        ).thenReturn(
                Collections.emptyList()
        );

        when(
                alertaRepository.save(
                        any(AlertaPanico.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        AlertaPanico alerta =
                alertaController.activarAlerta(
                        1,
                        null
                );

        assertNotNull(alerta);

        assertEquals(
                1,
                alerta.getIdGuarda()
        );

        assertEquals(
                "Activa",
                alerta.getEstado()
        );

        assertEquals(
                "Ubicación no registrada",
                alerta.getUbicacion()
        );

        assertNotNull(
                alerta.getFecha()
        );

        verify(
                alertaRepository
        ).save(
                any(AlertaPanico.class)
        );
    }

    @Test
    void cancelaAlertaExistente() {

        AlertaPanico alerta =
                new AlertaPanico();

        alerta.setIdGuarda(1);
        alerta.setEstado("Activa");

        when(
                alertaRepository.findById(1)
        ).thenReturn(
                Optional.of(alerta)
        );

        when(
                alertaRepository.save(
                        any(AlertaPanico.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        Map<String, Object> respuesta =
                alertaController.cancelarAlerta(1);

        assertEquals(
                "Alerta cancelada correctamente.",
                respuesta.get("mensaje")
        );

        AlertaPanico alertaCancelada =
                (AlertaPanico) respuesta.get("alerta");

        assertNotNull(alertaCancelada);

        assertEquals(
                "Cancelada",
                alertaCancelada.getEstado()
        );

        verify(
                alertaRepository
        ).save(
                alerta
        );
    }

    @Test
    void atiendeAlertaExistente() {

        AlertaPanico alerta =
                new AlertaPanico();

        alerta.setIdGuarda(1);
        alerta.setEstado("Activa");

        when(
                alertaRepository.findById(1)
        ).thenReturn(
                Optional.of(alerta)
        );

        when(
                alertaRepository.save(
                        any(AlertaPanico.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        Map<String, Object> respuesta =
                alertaController.atenderAlerta(1);

        assertEquals(
                "Alerta atendida correctamente.",
                respuesta.get("mensaje")
        );

        AlertaPanico alertaAtendida =
                (AlertaPanico) respuesta.get("alerta");

        assertNotNull(alertaAtendida);

        assertEquals(
                "Atendida",
                alertaAtendida.getEstado()
        );

        verify(
                alertaRepository
        ).save(
                alerta
        );
    }
}
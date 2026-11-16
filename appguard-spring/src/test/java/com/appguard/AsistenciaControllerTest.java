package com.appguard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.appguard.controller.AsistenciaController;
import com.appguard.model.Asistencia;
import com.appguard.repository.AsistenciaRepository;

class AsistenciaControllerTest {

    @Mock
    private AsistenciaRepository asistenciaRepository;

    private AsistenciaController asistenciaController;

    @BeforeEach
    void configurar() {

        MockitoAnnotations.openMocks(this);

        asistenciaController =
                new AsistenciaController(
                        asistenciaRepository
                );
    }

    @Test
    void registraAsistenciaConFechaHoraYEstado() {

        Asistencia asistencia =
                new Asistencia();

        asistencia.setIdGuarda(1);

        when(
                asistenciaRepository.save(
                        any(Asistencia.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        Asistencia resultado =
                asistenciaController
                        .registrarAsistencia(
                                asistencia
                        );

        assertNotNull(resultado);

        assertEquals(
                1,
                resultado.getIdGuarda()
        );

        assertNotNull(
                resultado.getFecha()
        );

        assertNotNull(
                resultado.getHoraEntrada()
        );

        assertNotNull(
                resultado.getEstado()
        );

        verify(
                asistenciaRepository
        ).save(
                any(Asistencia.class)
        );
    }

    @Test
    void informaCuandoNoExisteTurnoRegistrado() {

        when(
                asistenciaRepository
                        .findTopByIdGuardaAndFechaOrderByIdAsistenciaDesc(
                                eq(1),
                                any(LocalDate.class)
                        )
        ).thenReturn(
                Optional.empty()
        );

        Map<String, Object> respuesta =
                asistenciaController
                        .consultarEstadoTurno(1);

        assertEquals(
                "Sin turno registrado",
                respuesta.get("estadoTurno")
        );
    }

    @Test
    void finalizaTurnoActivo() {

        Asistencia asistencia =
                new Asistencia();

        asistencia.setIdGuarda(1);
        asistencia.setFecha(
                LocalDate.now()
        );
        asistencia.setHoraEntrada(
                LocalTime.of(6, 0)
        );
        asistencia.setEstado(
                "A tiempo"
        );

        when(
                asistenciaRepository
                        .findTopByIdGuardaAndFechaOrderByIdAsistenciaDesc(
                                eq(1),
                                any(LocalDate.class)
                        )
        ).thenReturn(
                Optional.of(asistencia)
        );

        when(
                asistenciaRepository.save(
                        any(Asistencia.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        Map<String, Object> respuesta =
                asistenciaController
                        .finalizarTurno(1);

        assertEquals(
                "Turno finalizado correctamente.",
                respuesta.get("mensaje")
        );

        assertEquals(
                "Finalizado",
                respuesta.get("estadoTurno")
        );

        assertNotNull(
                respuesta.get("horaSalida")
        );

        verify(
                asistenciaRepository
        ).save(
                asistencia
        );
    }
}
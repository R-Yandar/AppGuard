package com.appguard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.appguard.controller.TurnoController;
import com.appguard.model.Turno;
import com.appguard.repository.GuardaRepository;
import com.appguard.repository.PuestoServicioRepository;
import com.appguard.repository.TurnoRepository;
import com.appguard.repository.UsuarioRepository;

class TurnoControllerTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private PuestoServicioRepository puestoRepository;

    @Mock
    private GuardaRepository guardaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private TurnoController turnoController;

    @BeforeEach
    void configurar() {
        MockitoAnnotations.openMocks(this);

        turnoController = new TurnoController(
                turnoRepository,
                puestoRepository,
                guardaRepository,
                usuarioRepository
        );
    }

    @Test
    void rechazaTurnoConHoraFinAnteriorAHoraInicio() {

        Turno turno = new Turno();

        turno.setIdGuarda(1);
        turno.setIdPuesto(1);
        turno.setFecha(LocalDate.of(2026, 11, 16));

        turno.setHoraInicio(
                LocalTime.of(18, 0)
        );

        turno.setHoraFin(
                LocalTime.of(6, 0)
        );

        ResponseEntity<?> respuesta =
                turnoController.crearTurno(turno);

        assertEquals(
                400,
                respuesta.getStatusCode().value()
        );

        assertEquals(
                "La hora de fin debe ser posterior a la hora de inicio.",
                respuesta.getBody()
        );
    }

    @Test
    void asignaEstadoProgramadoAlCrearTurno() {

        Turno turno = new Turno();

        turno.setIdGuarda(1);
        turno.setIdPuesto(1);
        turno.setFecha(
                LocalDate.of(2026, 11, 16)
        );

        turno.setHoraInicio(
                LocalTime.of(6, 0)
        );

        turno.setHoraFin(
                LocalTime.of(14, 0)
        );

        when(
                turnoRepository
                        .findByIdGuardaAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
                                any(),
                                any(),
                                any(),
                                any()
                        )
        ).thenReturn(
                Collections.emptyList()
        );

        when(
                turnoRepository
                        .findByIdPuestoAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
                                any(),
                                any(),
                                any(),
                                any()
                        )
        ).thenReturn(
                Collections.emptyList()
        );

        when(
                turnoRepository.save(any(Turno.class))
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        ResponseEntity<?> respuesta =
                turnoController.crearTurno(turno);

        assertEquals(
                200,
                respuesta.getStatusCode().value()
        );

        Turno turnoGuardado =
                (Turno) respuesta.getBody();

        assertNotNull(turnoGuardado);

        assertEquals(
                "Programado",
                turnoGuardado.getEstado()
        );

        verify(
                turnoRepository
        ).save(
                any(Turno.class)
        );
    }
}
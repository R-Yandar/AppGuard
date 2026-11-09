package com.appguard.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Asistencia;
import com.appguard.repository.AsistenciaRepository;

/**
 * Controlador REST para la gestión de asistencias
 * del módulo Guardas de AppGuard.
 */
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    private final AsistenciaRepository asistenciaRepository;

    public AsistenciaController(
            AsistenciaRepository asistenciaRepository) {
        this.asistenciaRepository = asistenciaRepository;
    }

    /**
     * Consulta todos los registros de asistencia.
     */
    @GetMapping
    public List<Asistencia> listarAsistencias() {
        return asistenciaRepository.findAll();
    }

    /**
     * Registra la asistencia de un guarda.
     * La fecha y hora de entrada se generan en el servidor.
     */
    @PostMapping
    public Asistencia registrarAsistencia(
            @RequestBody Asistencia asistencia) {

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        asistencia.setFecha(fechaActual);
        asistencia.setHoraEntrada(horaActual);

        // Horario temporal para pruebas.
        LocalTime horaProgramada = LocalTime.of(6, 0);
        LocalTime limiteLlegada = horaProgramada.plusMinutes(10);

        if (horaActual.isAfter(limiteLlegada)) {
            asistencia.setEstado("Llegada tarde");
        } else {
            asistencia.setEstado("A tiempo");
        }

        return asistenciaRepository.save(asistencia);
    }
    /**
 * Consulta el estado del turno actual de un guarda.
 */
@GetMapping("/turno/{idGuarda}")
public Map<String, Object> consultarEstadoTurno(
        @PathVariable Integer idGuarda) {

    Optional<Asistencia> asistencia =
            asistenciaRepository
                    .findTopByIdGuardaAndFechaOrderByIdAsistenciaDesc(
                            idGuarda,
                            LocalDate.now()
                    );

    Map<String, Object> respuesta = new HashMap<>();

    if (asistencia.isEmpty()) {
        respuesta.put("estadoTurno", "Sin turno registrado");
        return respuesta;
    }

    Asistencia registro = asistencia.get();

    respuesta.put("idAsistencia", registro.getIdAsistencia());
    respuesta.put("idGuarda", registro.getIdGuarda());
    respuesta.put("fecha", registro.getFecha());
    respuesta.put("horaEntrada", registro.getHoraEntrada());
    respuesta.put(
            "horaSalida",
            registro.getHoraSalida() == null
                    ? "Pendiente"
                    : registro.getHoraSalida()
    );
    respuesta.put("asistencia", registro.getEstado());

    respuesta.put(
            "estadoTurno",
            registro.getHoraSalida() == null
                    ? "Activo"
                    : "Finalizado"
    );

    return respuesta;
}

/**
 * Finaliza el turno activo de un guarda.
 * Registra automáticamente la hora de salida.
 */
@PostMapping("/finalizar/{idGuarda}")
public Map<String, Object> finalizarTurno(
        @PathVariable Integer idGuarda) {

    Optional<Asistencia> asistencia =
            asistenciaRepository
                    .findTopByIdGuardaAndFechaOrderByIdAsistenciaDesc(
                            idGuarda,
                            LocalDate.now()
                    );

    Map<String, Object> respuesta = new HashMap<>();

    if (asistencia.isEmpty()) {
        respuesta.put(
                "mensaje",
                "No existe un turno activo para finalizar."
        );

        return respuesta;
    }

    Asistencia registro = asistencia.get();

    if (registro.getHoraSalida() != null) {
        respuesta.put(
                "mensaje",
                "El turno ya se encuentra finalizado."
        );

        return respuesta;
    }

    registro.setHoraSalida(LocalTime.now());

    asistenciaRepository.save(registro);

    respuesta.put(
            "mensaje",
            "Turno finalizado correctamente."
    );

    respuesta.put(
            "idAsistencia",
            registro.getIdAsistencia()
    );

    respuesta.put(
            "horaSalida",
            registro.getHoraSalida()
    );

    respuesta.put(
            "estadoTurno",
            "Finalizado"
    );

    return respuesta;
}

/**
 * Consulta el personal que se encuentra actualmente en servicio.
 */
@GetMapping("/personal-en-servicio")
public List<Map<String, Object>> listarPersonalEnServicio() {

    List<Asistencia> asistencias =
            asistenciaRepository.findAll();

    return asistencias.stream()
            .filter(registro ->
                    registro.getFecha().equals(LocalDate.now())
                    && registro.getHoraSalida() == null
            )
            .map(registro -> {
                Map<String, Object> item = new HashMap<>();

                item.put("idAsistencia", registro.getIdAsistencia());
                item.put("idGuarda", registro.getIdGuarda());
                item.put("fecha", registro.getFecha());
                item.put("horaEntrada", registro.getHoraEntrada());
                item.put("asistencia", registro.getEstado());
                item.put("estadoTurno", "Activo");

                return item;
            })
            .toList();
}

}
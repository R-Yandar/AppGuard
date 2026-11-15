package com.appguard.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.PuestoServicio;
import com.appguard.model.Turno;
import com.appguard.repository.PuestoServicioRepository;
import com.appguard.repository.TurnoRepository;
import com.appguard.model.Guarda;
import com.appguard.model.Usuario;
import com.appguard.repository.GuardaRepository;
import com.appguard.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://127.0.0.1:5173",
        "http://localhost:5174",
        "http://127.0.0.1:5174"
})
public class TurnoController {

    private final TurnoRepository turnoRepository;
private final PuestoServicioRepository puestoRepository;
private final GuardaRepository guardaRepository;
private final UsuarioRepository usuarioRepository;

    public TurnoController(
        TurnoRepository turnoRepository,
        PuestoServicioRepository puestoRepository,
        GuardaRepository guardaRepository,
        UsuarioRepository usuarioRepository) {

    this.turnoRepository = turnoRepository;
    this.puestoRepository = puestoRepository;
    this.guardaRepository = guardaRepository;
    this.usuarioRepository = usuarioRepository;
}

    @GetMapping
    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }

    @GetMapping("/{idTurno}")
    public ResponseEntity<Turno> buscarPorId(
            @PathVariable Integer idTurno) {

        return turnoRepository.findById(idTurno)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/fecha")
    public List<Turno> listarPorFecha(
            @RequestParam LocalDate fecha) {

        return turnoRepository
                .findByFechaOrderByHoraInicioAsc(fecha);
    }

    /*
     * Consulta utilizada por el módulo Horarios.
     * Devuelve el turno junto con la información del puesto.
     */
    @GetMapping("/guarda/{idGuarda}")
    public List<Map<String, Object>> listarPorGuarda(
            @PathVariable Integer idGuarda) {

        List<Turno> turnos =
                turnoRepository
                        .findByIdGuardaOrderByFechaDescHoraInicioAsc(
                                idGuarda
                        );

        List<Map<String, Object>> respuesta =
                new ArrayList<>();

        for (Turno turno : turnos) {

            Map<String, Object> datos =
                    new HashMap<>();

            datos.put(
                    "idTurno",
                    turno.getIdTurno()
            );

            datos.put(
                    "idGuarda",
                    turno.getIdGuarda()
            );

            datos.put(
                    "idPuesto",
                    turno.getIdPuesto()
            );

            datos.put(
                    "fecha",
                    turno.getFecha()
            );

            datos.put(
                    "horaInicio",
                    turno.getHoraInicio()
            );

            datos.put(
                    "horaFin",
                    turno.getHoraFin()
            );

            datos.put(
                    "estado",
                    turno.getEstado()
            );

            PuestoServicio puesto =
                    puestoRepository
                            .findById(turno.getIdPuesto())
                            .orElse(null);

            if (puesto != null) {

                datos.put(
                        "puesto",
                        puesto.getNombrePuesto()
                );

                datos.put(
                        "direccion",
                        puesto.getDireccion()
                );

                datos.put(
                        "ciudad",
                        puesto.getCiudad()
                );

            } else {

                datos.put(
                        "puesto",
                        "Puesto no registrado"
                );

                datos.put(
                        "direccion",
                        ""
                );

                datos.put(
                        "ciudad",
                        ""
                );
            }

            respuesta.add(datos);
        }

        return respuesta;
    }

    @GetMapping("/puesto/{idPuesto}")
    public List<Turno> listarPorPuesto(
            @PathVariable Integer idPuesto) {

        return turnoRepository
                .findByIdPuestoOrderByFechaDescHoraInicioAsc(
                        idPuesto
                );
    }

    @GetMapping("/puesto/{idPuesto}/personal")
public List<Map<String, Object>> listarPersonalPorPuesto(
        @PathVariable Integer idPuesto) {

    List<Turno> turnos =
            turnoRepository
                    .findByIdPuestoOrderByFechaDescHoraInicioAsc(
                            idPuesto
                    );

    List<Map<String, Object>> respuesta =
            new ArrayList<>();

    for (Turno turno : turnos) {

        Guarda guarda = guardaRepository
                .findById(turno.getIdGuarda())
                .orElse(null);

        if (guarda == null) {
            continue;
        }

        Usuario usuario = usuarioRepository
                .findById(guarda.getIdUsuario())
                .orElse(null);

        if (usuario == null) {
            continue;
        }

        Map<String, Object> datos =
                new HashMap<>();

        datos.put("idTurno", turno.getIdTurno());
        datos.put("idGuarda", guarda.getIdGuarda());

        datos.put(
                "nombreCompleto",
                usuario.getNombre() + " " + usuario.getApellido()
        );

        datos.put("documento", usuario.getDocumento());
        datos.put("codigoGuarda", guarda.getCodigoGuarda());
        datos.put("fecha", turno.getFecha());
        datos.put("horaInicio", turno.getHoraInicio());
        datos.put("horaFin", turno.getHoraFin());
        datos.put("estado", turno.getEstado());

        respuesta.add(datos);
    }

    return respuesta;
}

    @PostMapping
    public ResponseEntity<?> crearTurno(
            @RequestBody Turno turno) {

        if (turno.getIdGuarda() == null
                || turno.getIdPuesto() == null
                || turno.getFecha() == null
                || turno.getHoraInicio() == null
                || turno.getHoraFin() == null) {

            return ResponseEntity.badRequest().body(
                    "Guarda, puesto, fecha, hora de inicio y hora de fin son obligatorios."
            );
        }

        if (!turno.getHoraFin().isAfter(
                turno.getHoraInicio())) {

            return ResponseEntity.badRequest().body(
                    "La hora de fin debe ser posterior a la hora de inicio."
            );
        }

        var crucesGuarda =
                turnoRepository
                        .findByIdGuardaAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
                                turno.getIdGuarda(),
                                turno.getFecha(),
                                turno.getHoraFin(),
                                turno.getHoraInicio()
                        );

        if (!crucesGuarda.isEmpty()) {

            return ResponseEntity.badRequest().body(
                    "El guarda ya tiene un turno que se cruza con este horario."
            );
        }

        var crucesPuesto =
                turnoRepository
                        .findByIdPuestoAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
                                turno.getIdPuesto(),
                                turno.getFecha(),
                                turno.getHoraFin(),
                                turno.getHoraInicio()
                        );

        if (!crucesPuesto.isEmpty()) {

            return ResponseEntity.badRequest().body(
                    "El puesto ya tiene un turno asignado en ese horario."
            );
        }

        if (turno.getEstado() == null
                || turno.getEstado().isBlank()) {

            turno.setEstado("Programado");
        }

        Turno guardado =
                turnoRepository.save(turno);

        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{idTurno}")
    public ResponseEntity<?> actualizarTurno(
            @PathVariable Integer idTurno,
            @RequestBody Turno datos) {

        return turnoRepository
                .findById(idTurno)
                .map(turno -> {

                    turno.setIdGuarda(
                            datos.getIdGuarda()
                    );

                    turno.setIdPuesto(
                            datos.getIdPuesto()
                    );

                    turno.setFecha(
                            datos.getFecha()
                    );

                    turno.setHoraInicio(
                            datos.getHoraInicio()
                    );

                    turno.setHoraFin(
                            datos.getHoraFin()
                    );

                    turno.setEstado(
                            datos.getEstado()
                    );

                    return ResponseEntity.ok(
                            turnoRepository.save(turno)
                    );
                })
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @PutMapping("/{idTurno}/cancelar")
    public ResponseEntity<?> cancelarTurno(
            @PathVariable Integer idTurno) {

        return turnoRepository
                .findById(idTurno)
                .map(turno -> {

                    turno.setEstado("Cancelado");

                    return ResponseEntity.ok(
                            turnoRepository.save(turno)
                    );
                })
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{idTurno}")
    public ResponseEntity<?> eliminarTurno(
            @PathVariable Integer idTurno) {

        if (!turnoRepository.existsById(idTurno)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        turnoRepository.deleteById(idTurno);

        return ResponseEntity
                .noContent()
                .build();
    }
}
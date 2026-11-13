package com.appguard.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.appguard.model.AlertaPanico;
import com.appguard.model.Guarda;
import com.appguard.model.PuestoServicio;
import com.appguard.model.Turno;
import com.appguard.model.Usuario;
import com.appguard.repository.AlertaPanicoRepository;
import com.appguard.repository.GuardaRepository;
import com.appguard.repository.PuestoServicioRepository;
import com.appguard.repository.TurnoRepository;
import com.appguard.repository.UsuarioRepository;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/alertas")
public class AlertaPanicoController {

    private final AlertaPanicoRepository alertaRepository;
    private final GuardaRepository guardaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurnoRepository turnoRepository;
    private final PuestoServicioRepository puestoRepository;

    public AlertaPanicoController(
            AlertaPanicoRepository alertaRepository,
            GuardaRepository guardaRepository,
            UsuarioRepository usuarioRepository,
            TurnoRepository turnoRepository,
            PuestoServicioRepository puestoRepository) {

        this.alertaRepository = alertaRepository;
        this.guardaRepository = guardaRepository;
        this.usuarioRepository = usuarioRepository;
        this.turnoRepository = turnoRepository;
        this.puestoRepository = puestoRepository;
    }

    @GetMapping
    public List<Map<String, Object>> listarAlertas() {
        return construirRespuesta(alertaRepository.findAll());
    }

    @GetMapping("/activas")
    public List<Map<String, Object>> listarAlertasActivas() {
        return construirRespuesta(alertaRepository.findByEstado("Activa"));
    }

    private List<Map<String, Object>> construirRespuesta(
            List<AlertaPanico> alertas) {

        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (AlertaPanico alerta : alertas) {

            Map<String, Object> datos = new HashMap<>();

            datos.put("idAlerta", alerta.getIdAlerta());
            datos.put("idGuarda", alerta.getIdGuarda());
            datos.put("fecha", alerta.getFecha());
            datos.put("ubicacion", alerta.getUbicacion());
            datos.put("estado", alerta.getEstado());
            datos.put("idPuesto", alerta.getIdPuesto());

            Optional<Guarda> guardaOptional =
                    guardaRepository.findById(alerta.getIdGuarda());

            if (guardaOptional.isPresent()) {

                Guarda guarda = guardaOptional.get();

                datos.put("codigoGuarda", guarda.getCodigoGuarda());

                Optional<Usuario> usuarioOptional =
                        usuarioRepository.findById(guarda.getIdUsuario());

                if (usuarioOptional.isPresent()) {

                    Usuario usuario = usuarioOptional.get();

                    datos.put(
                            "nombreGuarda",
                            usuario.getNombre() + " " + usuario.getApellido()
                    );
                }

                if (alerta.getIdPuesto() != null) {

    Optional<PuestoServicio> puestoOptional =
            puestoRepository.findById(
                    alerta.getIdPuesto()
            );

    if (puestoOptional.isPresent()) {

        PuestoServicio puesto =
                puestoOptional.get();

        datos.put(
                "puesto",
                puesto.getNombrePuesto()
        );

        datos.put(
                "direccionPuesto",
                puesto.getDireccion()
        );
    }
}
            }

            respuesta.add(datos);
        }

        return respuesta;
    }

    @PutMapping("/{idAlerta}/atender")
    public Map<String, Object> atenderAlerta(
            @PathVariable Integer idAlerta) {

        Map<String, Object> respuesta = new HashMap<>();

        Optional<AlertaPanico> alertaOptional =
                alertaRepository.findById(idAlerta);

        if (alertaOptional.isEmpty()) {
            respuesta.put(
                    "mensaje",
                    "La alerta no existe."
            );

            return respuesta;
        }

        AlertaPanico alerta = alertaOptional.get();

        alerta.setEstado("Atendida");

        alertaRepository.save(alerta);

        respuesta.put(
                "mensaje",
                "Alerta atendida correctamente."
        );

        respuesta.put(
                "alerta",
                alerta
        );

        return respuesta;
    }

@PostMapping("/activar")
public AlertaPanico activarAlerta(
        @RequestParam(defaultValue = "1") Integer idGuarda,
        @RequestParam(required = false) String ubicacion) {

    AlertaPanico alerta = new AlertaPanico();

    alerta.setIdGuarda(idGuarda);
    List<Turno> turnos =
        turnoRepository.findByIdGuardaAndFecha(
                idGuarda,
                LocalDate.now()
        );

for (Turno turno : turnos) {

    if ("Activo".equalsIgnoreCase(turno.getEstado())
            || "Programado".equalsIgnoreCase(turno.getEstado())) {

        alerta.setIdPuesto(turno.getIdPuesto());

        Optional<PuestoServicio> puestoOptional =
                puestoRepository.findById(turno.getIdPuesto());

        if (puestoOptional.isPresent()) {

            PuestoServicio puesto = puestoOptional.get();

            alerta.setUbicacion(
                    puesto.getNombrePuesto()
                            + " - "
                            + puesto.getCiudad()
            );
        }

        break;
    }
}
    alerta.setFecha(LocalDateTime.now());
   if (alerta.getUbicacion() == null) {
    alerta.setUbicacion(
            ubicacion != null && !ubicacion.isBlank()
                    ? ubicacion
                    : "Ubicación no registrada"
    );
}
    alerta.setEstado("Activa");

    return alertaRepository.save(alerta);
}

@PutMapping("/{idAlerta}/cancelar")
public Map<String, Object> cancelarAlerta(
        @PathVariable Integer idAlerta) {

    Map<String, Object> respuesta = new HashMap<>();

    Optional<AlertaPanico> alertaOptional =
            alertaRepository.findById(idAlerta);

    if (alertaOptional.isEmpty()) {
        respuesta.put("mensaje", "La alerta no existe.");
        return respuesta;
    }

    AlertaPanico alerta = alertaOptional.get();

    alerta.setEstado("Cancelada");
    alertaRepository.save(alerta);

    respuesta.put("mensaje", "Alerta cancelada correctamente.");
    respuesta.put("alerta", alerta);

    return respuesta;

}

}
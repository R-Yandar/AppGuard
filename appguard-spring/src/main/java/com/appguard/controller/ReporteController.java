package com.appguard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Novedad;
import com.appguard.model.Usuario;
import com.appguard.repository.NovedadRepository;
import com.appguard.repository.UsuarioRepository;

import com.appguard.model.Guarda;
import com.appguard.model.PuestoServicio;
import com.appguard.model.Turno;

import com.appguard.repository.GuardaRepository;
import com.appguard.repository.PuestoServicioRepository;
import com.appguard.repository.TurnoRepository;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final NovedadRepository novedadRepository;
    private final UsuarioRepository usuarioRepository;
private final TurnoRepository turnoRepository;
private final GuardaRepository guardaRepository;
private final PuestoServicioRepository puestoRepository;

    public ReporteController(
        NovedadRepository novedadRepository,
        UsuarioRepository usuarioRepository,
        TurnoRepository turnoRepository,
        GuardaRepository guardaRepository,
        PuestoServicioRepository puestoRepository) {

    this.novedadRepository = novedadRepository;
    this.usuarioRepository = usuarioRepository;
    this.turnoRepository = turnoRepository;
    this.guardaRepository = guardaRepository;
    this.puestoRepository = puestoRepository;
}

    @GetMapping("/novedades-guardas")
    public List<Map<String, Object>> novedadesGuardas() {
        return filtrarNovedadesPorRol(3);
    }

    @GetMapping("/novedades-supervisores")
    public List<Map<String, Object>> novedadesSupervisores() {
        return filtrarNovedadesPorRol(2);
    }

@GetMapping("/puestos")
public List<Map<String, Object>> reportesPuestos() {

    List<Turno> turnos = turnoRepository.findAll();
    List<Map<String, Object>> resultado = new ArrayList<>();

    for (Turno turno : turnos) {

        Map<String, Object> datos = new HashMap<>();

        datos.put("idTurno", turno.getIdTurno());
        datos.put("fecha", turno.getFecha());
        datos.put("horaInicio", turno.getHoraInicio());
        datos.put("horaFin", turno.getHoraFin());
        datos.put("estado", turno.getEstado());

        Optional<PuestoServicio> puestoOptional =
                puestoRepository.findById(turno.getIdPuesto());

        if (puestoOptional.isPresent()) {
            PuestoServicio puesto = puestoOptional.get();

            datos.put("puesto", puesto.getNombrePuesto());
            datos.put("direccion", puesto.getDireccion());
            datos.put("ciudad", puesto.getCiudad());
        }

        Optional<Guarda> guardaOptional =
                guardaRepository.findById(turno.getIdGuarda());

        if (guardaOptional.isPresent()) {

            Guarda guarda = guardaOptional.get();

            datos.put("codigoGuarda", guarda.getCodigoGuarda());

            Optional<Usuario> usuarioOptional =
                    usuarioRepository.findById(guarda.getIdUsuario());

            if (usuarioOptional.isPresent()) {

                Usuario usuario = usuarioOptional.get();

                datos.put(
                        "guarda",
                        usuario.getNombre() + " " + usuario.getApellido()
                );
            }
        }

        resultado.add(datos);
    }

    return resultado;
}

    private List<Map<String, Object>> filtrarNovedadesPorRol(
            Integer idRol) {

        List<Novedad> novedades = novedadRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Novedad novedad : novedades) {

            if (novedad.getIdUsuario() == null) {
                continue;
            }

            Optional<Usuario> usuarioOptional =
                    usuarioRepository.findById(
                            novedad.getIdUsuario()
                    );

            if (usuarioOptional.isEmpty()) {
                continue;
            }

            Usuario usuario = usuarioOptional.get();

            if (!idRol.equals(usuario.getIdRol())) {
                continue;
            }

            Map<String, Object> datos = new HashMap<>();

            datos.put("idNovedad", novedad.getIdNovedad());
            datos.put("nombre",
                    usuario.getNombre()
                            + " "
                            + usuario.getApellido());

            datos.put("documento", usuario.getDocumento());
            datos.put("tipo", novedad.getTipo());
            datos.put("descripcion", novedad.getDescripcion());
            datos.put("fecha", novedad.getFecha());
            datos.put("hora", novedad.getHora());

            resultado.add(datos);
        }

        return resultado;
    }
}
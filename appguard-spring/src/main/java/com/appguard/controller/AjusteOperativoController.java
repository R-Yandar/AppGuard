package com.appguard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Guarda;
import com.appguard.model.PuestoServicio;
import com.appguard.model.Turno;
import com.appguard.model.Usuario;
import com.appguard.repository.GuardaRepository;
import com.appguard.repository.PuestoServicioRepository;
import com.appguard.repository.TurnoRepository;
import com.appguard.repository.UsuarioRepository;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/ajustes-operativos")
public class AjusteOperativoController {

    private final TurnoRepository turnoRepository;
    private final GuardaRepository guardaRepository;
    private final PuestoServicioRepository puestoRepository;
    private final UsuarioRepository usuarioRepository;

    public AjusteOperativoController(
            TurnoRepository turnoRepository,
            GuardaRepository guardaRepository,
            PuestoServicioRepository puestoRepository,
            UsuarioRepository usuarioRepository) {

        this.turnoRepository = turnoRepository;
        this.guardaRepository = guardaRepository;
        this.puestoRepository = puestoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/turnos")
    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }

    @GetMapping("/guardas")
    public List<Map<String, Object>> listarGuardasActivos() {

        List<Guarda> guardas =
                guardaRepository.findByEstado("Activo");

        List<Map<String, Object>> resultado =
                new ArrayList<>();

        for (Guarda guarda : guardas) {

            Optional<Usuario> usuarioOptional =
                    usuarioRepository.findById(
                            guarda.getIdUsuario()
                    );

            if (usuarioOptional.isPresent()) {

                Usuario usuario = usuarioOptional.get();

                Map<String, Object> item =
                        new HashMap<>();

                item.put(
                        "idGuarda",
                        guarda.getIdGuarda()
                );

                item.put(
                        "codigoGuarda",
                        guarda.getCodigoGuarda()
                );

                item.put(
                        "nombre",
                        usuario.getNombre()
                );

                item.put(
                        "apellido",
                        usuario.getApellido()
                );

                item.put(
                        "documento",
                        usuario.getDocumento()
                );

                item.put(
                        "estado",
                        guarda.getEstado()
                );

                resultado.add(item);
            }
        }

        return resultado;
    }

    @GetMapping("/puestos")
    public List<PuestoServicio> listarPuestosActivos() {
        return puestoRepository.findByEstado("Activo");
    }

    @PutMapping("/turnos/{idTurno}")
    public Map<String, Object> actualizarTurno(
            @PathVariable Integer idTurno,
            @RequestBody Turno datosActualizados) {

        Map<String, Object> respuesta =
                new HashMap<>();

        Optional<Turno> turnoOptional =
                turnoRepository.findById(idTurno);

        if (turnoOptional.isEmpty()) {
            respuesta.put(
                    "mensaje",
                    "El turno no existe."
            );

            return respuesta;
        }

        Turno turno = turnoOptional.get();

        turno.setIdGuarda(
                datosActualizados.getIdGuarda()
        );

        turno.setIdPuesto(
                datosActualizados.getIdPuesto()
        );

        turno.setFecha(
                datosActualizados.getFecha()
        );

        turno.setHoraInicio(
                datosActualizados.getHoraInicio()
        );

        turno.setHoraFin(
                datosActualizados.getHoraFin()
        );

        turno.setEstado(
                datosActualizados.getEstado()
        );

        turnoRepository.save(turno);

        respuesta.put(
                "mensaje",
                "Ajuste operativo guardado correctamente."
        );

        respuesta.put(
                "turno",
                turno
        );

        return respuesta;
    }
}
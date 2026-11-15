package com.appguard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Guarda;
import com.appguard.model.Usuario;
import com.appguard.repository.GuardaRepository;
import com.appguard.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/guardas")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://127.0.0.1:5173",
        "http://localhost:5174",
        "http://127.0.0.1:5174"
})
public class GuardaController {

    private final GuardaRepository guardaRepository;
    private final UsuarioRepository usuarioRepository;

    public GuardaController(
            GuardaRepository guardaRepository,
            UsuarioRepository usuarioRepository) {

        this.guardaRepository = guardaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Map<String, Object>> listarGuardas() {

        List<Guarda> guardas = guardaRepository.findAll();

        return construirRespuesta(guardas);
    }

    @GetMapping("/buscar")
    public List<Map<String, Object>> buscarGuardas(
            @RequestParam String texto) {

        String busqueda = texto
                .trim()
                .toLowerCase();

        List<Guarda> guardasEncontrados = new ArrayList<>();

        for (Guarda guarda : guardaRepository.findAll()) {

            Usuario usuario = usuarioRepository
                    .findById(guarda.getIdUsuario())
                    .orElse(null);

            if (usuario == null) {
                continue;
            }

            String nombre = usuario.getNombre() != null
                    ? usuario.getNombre().toLowerCase()
                    : "";

            String apellido = usuario.getApellido() != null
                    ? usuario.getApellido().toLowerCase()
                    : "";

            String nombreCompleto =
                    (nombre + " " + apellido).trim();

            String documento = usuario.getDocumento() != null
                    ? usuario.getDocumento().toLowerCase()
                    : "";

            String codigo = guarda.getCodigoGuarda() != null
                    ? guarda.getCodigoGuarda().toLowerCase()
                    : "";

            String id = guarda.getIdGuarda() != null
                    ? guarda.getIdGuarda().toString()
                    : "";

            if (
                    nombre.contains(busqueda)
                    || apellido.contains(busqueda)
                    || nombreCompleto.contains(busqueda)
                    || documento.contains(busqueda)
                    || codigo.contains(busqueda)
                    || id.equals(busqueda)
            ) {
                guardasEncontrados.add(guarda);
            }
        }

        return construirRespuesta(guardasEncontrados);
    }

    private List<Map<String, Object>> construirRespuesta(
            List<Guarda> guardas) {

        List<Map<String, Object>> respuesta =
                new ArrayList<>();

        for (Guarda guarda : guardas) {

            Usuario usuario = usuarioRepository
                    .findById(guarda.getIdUsuario())
                    .orElse(null);

            if (usuario == null) {
                continue;
            }

            Map<String, Object> datos =
                    new HashMap<>();

            datos.put(
                    "idGuarda",
                    guarda.getIdGuarda()
            );

            datos.put(
                    "codigoGuarda",
                    guarda.getCodigoGuarda()
            );

            datos.put(
                    "nombre",
                    usuario.getNombre()
            );

            datos.put(
                    "apellido",
                    usuario.getApellido()
            );

            datos.put(
                    "nombreCompleto",
                    usuario.getNombre()
                            + " "
                            + usuario.getApellido()
            );

            datos.put(
                    "documento",
                    usuario.getDocumento()
            );

            datos.put(
                    "estado",
                    guarda.getEstado()
            );

            datos.put(
                    "fechaIngreso",
                    guarda.getFechaIngreso()
            );

            respuesta.add(datos);
        }

        return respuesta;
    }
}
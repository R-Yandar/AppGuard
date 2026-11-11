package com.appguard.controller;

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

import com.appguard.model.AlertaPanico;
import com.appguard.repository.AlertaPanicoRepository;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/alertas")
public class AlertaPanicoController {

    private final AlertaPanicoRepository alertaRepository;

    public AlertaPanicoController(
            AlertaPanicoRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    @GetMapping
    public List<AlertaPanico> listarAlertas() {
        return alertaRepository.findAll();
    }

    @GetMapping("/activas")
    public List<AlertaPanico> listarAlertasActivas() {
        return alertaRepository.findByEstado("Activa");
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
}
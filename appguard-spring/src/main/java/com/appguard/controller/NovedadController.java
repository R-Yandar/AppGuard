package com.appguard.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.appguard.model.EvidenciaNovedad;
import com.appguard.model.Novedad;
import com.appguard.repository.EvidenciaNovedadRepository;
import com.appguard.repository.NovedadRepository;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/novedades")
public class NovedadController {

    private final NovedadRepository novedadRepository;
    private final EvidenciaNovedadRepository evidenciaRepository;

    private static final String CARPETA_EVIDENCIAS = "evidencias-novedades";

    public NovedadController(
            NovedadRepository novedadRepository,
            EvidenciaNovedadRepository evidenciaRepository) {

        this.novedadRepository = novedadRepository;
        this.evidenciaRepository = evidenciaRepository;
    }

    @GetMapping
    public List<Novedad> listarNovedades() {
        return novedadRepository.findAll();
    }

    @PostMapping
    public Novedad registrarNovedad(
            @RequestParam Integer idUsuario,
            @RequestParam String tipo,
            @RequestParam String descripcion,
            @RequestParam(required = false) MultipartFile[] archivos)
            throws IOException {

        Novedad novedad = new Novedad();

        novedad.setIdUsuario(idUsuario);
        novedad.setTipo(tipo);
        novedad.setDescripcion(descripcion);
        novedad.setFecha(LocalDate.now());
        novedad.setHora(LocalTime.now());
        novedad.setEstado("Pendiente");

        Novedad novedadGuardada =
                novedadRepository.save(novedad);

        if (archivos != null && archivos.length > 0) {

            if (archivos.length > 10) {
                throw new IllegalArgumentException(
                        "Solo se permiten máximo 10 archivos."
                );
            }

            Path carpeta = Paths.get(CARPETA_EVIDENCIAS);

            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }

            List<EvidenciaNovedad> evidencias = new ArrayList<>();

            for (MultipartFile archivo : archivos) {

                if (archivo.isEmpty()) {
                    continue;
                }

                String nombreOriginal =
                        archivo.getOriginalFilename();

                String nombreGuardado =
                        System.currentTimeMillis()
                                + "_"
                                + nombreOriginal;

                Path rutaArchivo =
                        carpeta.resolve(nombreGuardado);

                Files.copy(
                        archivo.getInputStream(),
                        rutaArchivo
                );

                EvidenciaNovedad evidencia =
                        new EvidenciaNovedad();

                evidencia.setIdNovedad(
                        novedadGuardada.getIdNovedad()
                );

                evidencia.setNombreArchivo(
                        nombreOriginal
                );

                evidencia.setTipoArchivo(
                        archivo.getContentType()
                );

                evidencia.setRutaArchivo(
                        rutaArchivo.toString()
                );

                evidencias.add(evidencia);
            }

            evidenciaRepository.saveAll(evidencias);
        }

        return novedadGuardada;
    }
}
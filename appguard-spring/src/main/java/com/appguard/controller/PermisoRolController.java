package com.appguard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.PermisoRol;
import com.appguard.repository.PermisoRolRepository;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/permisos")
public class PermisoRolController {

    private final PermisoRolRepository permisoRepository;

    public PermisoRolController(
            PermisoRolRepository permisoRepository) {

        this.permisoRepository = permisoRepository;
    }

    @GetMapping("/rol/{idRol}")
    public List<PermisoRol> listarPorRol(
            @PathVariable Integer idRol) {

        return permisoRepository.findByIdRol(idRol);
    }

    @PutMapping("/{idPermiso}/cambiar")
    public PermisoRol cambiarPermiso(
            @PathVariable Integer idPermiso) {

        Optional<PermisoRol> permisoOptional =
                permisoRepository.findById(idPermiso);

        if (permisoOptional.isEmpty()) {
            throw new RuntimeException("El permiso no existe.");
        }

        PermisoRol permiso = permisoOptional.get();

        permiso.setPermitido(
                !Boolean.TRUE.equals(permiso.getPermitido())
        );

        return permisoRepository.save(permiso);
    }
}
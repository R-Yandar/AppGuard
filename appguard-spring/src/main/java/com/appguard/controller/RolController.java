package com.appguard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Rol;
import com.appguard.repository.RolRepository;

@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173"
})
@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolRepository rolRepository;

    public RolController(
            RolRepository rolRepository) {

        this.rolRepository = rolRepository;
    }

    @GetMapping
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }
}
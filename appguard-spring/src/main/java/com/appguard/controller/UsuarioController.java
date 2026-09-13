package com.appguard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Usuario;
import com.appguard.repository.UsuarioRepository;

/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con los usuarios de AppGuard.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    /**
     * Inyección de dependencias por constructor.
     */
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Consulta todos los usuarios registrados.
     */
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Registra un nuevo usuario.
     */
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {

        if (usuario.getEstado() == null || usuario.getEstado().isBlank()) {
            usuario.setEstado("Activo");
        }

        return usuarioRepository.save(usuario);
    }
}
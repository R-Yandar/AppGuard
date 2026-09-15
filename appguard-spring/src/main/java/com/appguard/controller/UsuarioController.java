package com.appguard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Usuario;
import com.appguard.repository.UsuarioRepository;

/**
 * Controlador REST encargado de gestionar las operaciones
 * relacionadas con los usuarios de AppGuard.
 */
@CrossOrigin(origins = {
    "http://127.0.0.1:5501",
    "http://localhost:5501"
})
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
    @PutMapping
public Usuario actualizarUsuario(@RequestBody Usuario usuario) {

    if (usuario.getIdUsuario() == null) {
        throw new RuntimeException("El ID del usuario es obligatorio.");
    }

    Usuario existente = usuarioRepository.findById(usuario.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

    existente.setNombre(usuario.getNombre());
    existente.setApellido(usuario.getApellido());
    existente.setDocumento(usuario.getDocumento());
    existente.setCorreo(usuario.getCorreo());
    existente.setTelefono(usuario.getTelefono());
    existente.setIdRol(usuario.getIdRol());
    existente.setEstado(usuario.getEstado());

    return usuarioRepository.save(existente);
}

@DeleteMapping("/{id}")
public void eliminarUsuario(@PathVariable Integer id) {

    if (!usuarioRepository.existsById(id)) {
        throw new RuntimeException("Usuario no encontrado.");
    }

    usuarioRepository.deleteById(id);
}

}
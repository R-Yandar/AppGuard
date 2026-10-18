package com.appguard.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Usuario;
import com.appguard.service.AuthService;

/**
 * Controlador encargado de gestionar el inicio de sesión
 * de los usuarios de AppGuard.
 */
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://127.0.0.1:5173",
    "http://localhost:5501",
    "http://127.0.0.1:5501"
})

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Valida las credenciales recibidas desde el front-end.
     *
     * @param datos documento y contraseña del usuario
     * @return respuesta de autenticación
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Map<String, String> datos) {

        String documento = datos.get("documento");
        String contrasena = datos.get("contrasena");

        Map<String, Object> respuesta = new HashMap<>();

        if (documento == null || contrasena == null ||
            documento.isBlank() || contrasena.isBlank()) {

            respuesta.put("mensaje", "Documento y contraseña son obligatorios.");

            return ResponseEntity
                    .badRequest()
                    .body(respuesta);
        }

        Optional<Usuario> usuario =
                authService.autenticar(documento, contrasena);

        if (usuario.isEmpty()) {

    respuesta.put(
        "mensaje",
        "Error en la autenticación. Verifique sus credenciales."
    );

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(respuesta);
}

        Usuario usuarioAutenticado = usuario.get();

        respuesta.put("mensaje", "Autenticación satisfactoria.");
        respuesta.put("idUsuario", usuarioAutenticado.getIdUsuario());
        respuesta.put("nombre", usuarioAutenticado.getNombre());
        respuesta.put("apellido", usuarioAutenticado.getApellido());
        respuesta.put("idRol", usuarioAutenticado.getIdRol());

        return ResponseEntity.ok(respuesta);
    }

    /**
 * Activa la cuenta de un usuario registrado.
 *
 * @param datos documento, correo y contraseña
 * @return respuesta de activación
 */
@PostMapping("/activar")
public ResponseEntity<Map<String, Object>> activarCuenta(
        @RequestBody Map<String, String> datos) {

    String documento = datos.get("documento");
String correo = datos.get("correo");
String codigoActivacion = datos.get("codigoActivacion");
String contrasena = datos.get("contrasena");

    Map<String, Object> respuesta = new HashMap<>();

   if (documento == null || correo == null ||
    codigoActivacion == null || contrasena == null ||
    documento.isBlank() || correo.isBlank() ||
    codigoActivacion.isBlank() || contrasena.isBlank()) {

        respuesta.put(
            "mensaje",
            "Documento, correo y contraseña son obligatorios."
        );

        return ResponseEntity
                .badRequest()
                .body(respuesta);
    }

    Optional<Usuario> usuario =
    authService.activarCuenta(
        documento,
        correo,
        codigoActivacion,
        contrasena
    );

    if (usuario.isEmpty()) {

    respuesta.put(
        "mensaje",
        "La cuenta ya se encuentra activada o los datos ingresados no son correctos."
    );

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(respuesta);
}

    Usuario usuarioActivado = usuario.get();

    respuesta.put(
        "mensaje",
        "Cuenta activada correctamente."
    );

    respuesta.put(
        "idUsuario",
        usuarioActivado.getIdUsuario()
    );

    return ResponseEntity.ok(respuesta);
}

}
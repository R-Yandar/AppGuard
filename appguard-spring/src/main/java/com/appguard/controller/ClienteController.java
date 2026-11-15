package com.appguard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appguard.model.Cliente;
import com.appguard.model.PuestoServicio;
import com.appguard.repository.ClienteRepository;
import com.appguard.repository.PuestoServicioRepository;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://127.0.0.1:5173",
        "http://localhost:5174",
        "http://127.0.0.1:5174"
})
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final PuestoServicioRepository puestoRepository;

    public ClienteController(
            ClienteRepository clienteRepository,
            PuestoServicioRepository puestoRepository) {

        this.clienteRepository = clienteRepository;
        this.puestoRepository = puestoRepository;
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> buscarPorId(
            @PathVariable Integer idCliente) {

        return clienteRepository
                .findById(idCliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Cliente> buscarCliente(
            @RequestParam String texto) {

        List<Cliente> porNombre =
                clienteRepository
                        .findByNombreClienteContainingIgnoreCase(
                                texto
                        );

        if (!porNombre.isEmpty()) {
            return porNombre;
        }

        return clienteRepository
                .findByNitContainingIgnoreCase(
                        texto
                );
    }

    /*
     * Puestos pertenecientes a un cliente.
     */
    @GetMapping("/{idCliente}/puestos")
    public List<PuestoServicio> listarPuestosCliente(
            @PathVariable Integer idCliente) {

        return puestoRepository
                .findAll()
                .stream()
                .filter(puesto ->
                        puesto.getIdCliente() != null
                        && puesto.getIdCliente().equals(idCliente)
                )
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(
            @RequestBody Cliente cliente) {

        if (cliente.getNombreCliente() == null
                || cliente.getNombreCliente().isBlank()) {

            return ResponseEntity.badRequest().body(
                    "El nombre del cliente es obligatorio."
            );
        }

        if (cliente.getEstado() == null
                || cliente.getEstado().isBlank()) {

            cliente.setEstado("Activo");
        }

        return ResponseEntity.ok(
                clienteRepository.save(cliente)
        );
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<?> actualizarCliente(
            @PathVariable Integer idCliente,
            @RequestBody Cliente datos) {

        return clienteRepository
                .findById(idCliente)
                .map(cliente -> {

                    cliente.setNombreCliente(
                            datos.getNombreCliente()
                    );

                    cliente.setNit(
                            datos.getNit()
                    );

                    cliente.setDireccion(
                            datos.getDireccion()
                    );

                    cliente.setTelefono(
                            datos.getTelefono()
                    );

                    cliente.setCorreo(
                            datos.getCorreo()
                    );

                    cliente.setEstado(
                            datos.getEstado()
                    );

                    return ResponseEntity.ok(
                            clienteRepository.save(cliente)
                    );
                })
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @PutMapping("/{idCliente}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Integer idCliente) {

        return clienteRepository
                .findById(idCliente)
                .map(cliente -> {

                    if ("Activo".equalsIgnoreCase(
                            cliente.getEstado())) {

                        cliente.setEstado("Inactivo");

                    } else {

                        cliente.setEstado("Activo");
                    }

                    return ResponseEntity.ok(
                            clienteRepository.save(cliente)
                    );
                })
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }
}
package com.appguard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.Cliente;

public interface ClienteRepository
        extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByEstado(String estado);

    List<Cliente> findByNombreClienteContainingIgnoreCase(
            String nombreCliente
    );

    List<Cliente> findByNitContainingIgnoreCase(
            String nit
    );
}
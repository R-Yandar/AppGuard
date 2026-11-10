package com.appguard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.PuestoServicio;

public interface PuestoServicioRepository
        extends JpaRepository<PuestoServicio, Integer> {

    List<PuestoServicio> findByEstado(String estado);
}
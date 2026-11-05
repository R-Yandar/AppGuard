package com.appguard.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.Asistencia;

public interface AsistenciaRepository
        extends JpaRepository<Asistencia, Integer> {

    Optional<Asistencia> findTopByIdGuardaAndFechaOrderByIdAsistenciaDesc(
            Integer idGuarda,
            LocalDate fecha
    );
}
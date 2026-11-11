package com.appguard.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {

    List<Turno> findByIdGuardaAndFecha(
            Integer idGuarda,
            LocalDate fecha
    );

    List<Turno> findByIdGuardaAndFechaAndHoraInicioLessThanAndHoraFinGreaterThan(
            Integer idGuarda,
            LocalDate fecha,
            LocalTime horaFin,
            LocalTime horaInicio
    );
}
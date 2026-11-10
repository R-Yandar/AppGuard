package com.appguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {
}
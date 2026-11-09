package com.appguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.Novedad;

public interface NovedadRepository extends JpaRepository<Novedad, Integer> {
}
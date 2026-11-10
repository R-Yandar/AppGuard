package com.appguard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.Guarda;

public interface GuardaRepository extends JpaRepository<Guarda, Integer> {

    List<Guarda> findByEstado(String estado);
}
package com.appguard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.EvidenciaNovedad;

public interface EvidenciaNovedadRepository
        extends JpaRepository<EvidenciaNovedad, Integer> {

    List<EvidenciaNovedad> findByIdNovedad(Integer idNovedad);
}
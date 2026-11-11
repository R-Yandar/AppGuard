package com.appguard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.AlertaPanico;

public interface AlertaPanicoRepository
        extends JpaRepository<AlertaPanico, Integer> {

    List<AlertaPanico> findByEstado(String estado);
}
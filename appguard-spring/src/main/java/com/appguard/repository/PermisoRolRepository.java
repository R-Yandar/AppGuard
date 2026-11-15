package com.appguard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.PermisoRol;

public interface PermisoRolRepository
        extends JpaRepository<PermisoRol, Integer> {

    List<PermisoRol> findByIdRol(Integer idRol);
}
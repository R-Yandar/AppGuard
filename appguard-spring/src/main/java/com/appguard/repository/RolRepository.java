package com.appguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appguard.model.Rol;

public interface RolRepository
        extends JpaRepository<Rol, Integer> {
}
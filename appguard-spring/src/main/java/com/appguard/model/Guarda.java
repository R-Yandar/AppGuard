package com.appguard.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "guardas")
public class Guarda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGuarda;

    private Integer idUsuario;

    private String codigoGuarda;

    private LocalDate fechaIngreso;

    private String estado;

    public Guarda() {
    }

    public Integer getIdGuarda() {
        return idGuarda;
    }

    public void setIdGuarda(Integer idGuarda) {
        this.idGuarda = idGuarda;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigoGuarda() {
        return codigoGuarda;
    }

    public void setCodigoGuarda(String codigoGuarda) {
        this.codigoGuarda = codigoGuarda;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
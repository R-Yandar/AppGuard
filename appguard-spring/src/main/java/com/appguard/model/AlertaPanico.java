package com.appguard.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alertas_panico")
public class AlertaPanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlerta;

    private Integer idGuarda;

    private LocalDateTime fecha;

    private String ubicacion;

    private String estado;

    public AlertaPanico() {
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Integer getIdGuarda() {
        return idGuarda;
    }

    public void setIdGuarda(Integer idGuarda) {
        this.idGuarda = idGuarda;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
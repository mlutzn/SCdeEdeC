package org.example.scdeedec.componentes.mantenimientos.modelo;

import java.util.Date;

public class Mantenimiento {
    private int idMantenimiento;
    private int idEquipo;
    private String descripcion;
    private Date fecha;
    private String tipo;
    private String tecnico;
    private String observaciones;
    private Date fechaRegistro;

    public Mantenimiento() {}

    public Mantenimiento(int idEquipo, String descripcion, Date fecha, String tipo, String tecnico, String observaciones) {
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.tecnico = tecnico;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(int idMantenimiento) { this.idMantenimiento = idMantenimiento; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
package reparaciones.modelo;

import java.util.Date;

public class Reparacion {

    private int idReparacion;
    private int idEquipo;
    private Date fechaIngreso;
    private String fallaReportada;
    private String diagnostico;
    private String solucion;
    private String tecnico;
    private double costo;
    private String estado;
    private Date fechaEntrega;
    private String observaciones;
    private Date fechaRegistro;

    public Reparacion() {
    }

    public Reparacion(int idEquipo, Date fechaIngreso, String fallaReportada, String diagnostico,
                       String solucion, String tecnico, double costo, String estado,
                       Date fechaEntrega, String observaciones) {
        this.idEquipo = idEquipo;
        this.fechaIngreso = fechaIngreso;
        this.fallaReportada = fallaReportada;
        this.diagnostico = diagnostico;
        this.solucion = solucion;
        this.tecnico = tecnico;
        this.costo = costo;
        this.estado = estado;
        this.fechaEntrega = fechaEntrega;
        this.observaciones = observaciones;
    }

    public int getIdReparacion() { return idReparacion; }
    public void setIdReparacion(int idReparacion) { this.idReparacion = idReparacion; }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getFallaReportada() { return fallaReportada; }
    public void setFallaReportada(String fallaReportada) { this.fallaReportada = fallaReportada; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getSolucion() { return solucion; }
    public void setSolucion(String solucion) { this.solucion = solucion; }

    public String getTecnico() { return tecnico; }
    public void setTecnico(String tecnico) { this.tecnico = tecnico; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(Date fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}

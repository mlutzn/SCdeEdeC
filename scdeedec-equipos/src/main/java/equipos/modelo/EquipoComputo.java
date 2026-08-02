package equipos.modelo;

import java.util.Date;

public class EquipoComputo {

    private int idEquipo;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private Date fechaAdquisicion;
    private String estado;
    private String ubicacion;

    public EquipoComputo() {
    }

    public EquipoComputo(
            String tipo,
            String marca,
            String modelo,
            String numeroSerie,
            Date fechaAdquisicion,
            String estado,
            String ubicacion
    ) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
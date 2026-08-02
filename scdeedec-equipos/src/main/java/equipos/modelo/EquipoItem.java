package equipos.modelo;

/**
 * Objeto liviano para mostrar equipos en el JComboBox de Usuario.
 * No es el modelo completo de Equipo (eso sería otra clase si armás el módulo
 * de Equipos como tal); solo trae lo mínimo para listar y seleccionar.
 */
public class EquipoItem {
    private int idEquipo;
    private String descripcion;

    public EquipoItem() {
    }

    public EquipoItem(int idEquipo, String descripcion) {
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
    }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // Fundamental: es lo que el JComboBox muestra por defecto para cada item
    @Override
    public String toString() {
        return descripcion;
    }

    // Útil si en algún momento comparás EquipoItem por ID (ej. en un Set o para buscar en una lista)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EquipoItem)) return false;
        return idEquipo == ((EquipoItem) obj).idEquipo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idEquipo);
    }
}
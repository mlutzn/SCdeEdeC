package modelo;

// Clase auxiliar solo para mostrar equipos en el combo del formulario de Usuario.
// No reemplaza al futuro modelo completo de Equipo del Módulo 2.
public class EquipoItem {
    private final int idEquipo;
    private final String descripcion;

    public EquipoItem(int idEquipo, String descripcion) {
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
    }

    public int getIdEquipo() { return idEquipo; }

    @Override
    public String toString() {
        return descripcion; // esto es lo que se ve en el JComboBox
    }
}
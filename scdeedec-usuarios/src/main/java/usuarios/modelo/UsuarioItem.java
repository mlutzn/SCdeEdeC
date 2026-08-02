package usuarios.modelo;

/**
 * Objeto liviano para mostrar usuarios en el JComboBox de Técnico (Mantenimiento).
 * Igual idea que EquipoItem: solo lo mínimo para listar y seleccionar.
 */
public class UsuarioItem {
    private int idUsuario;
    private String nombreCompleto;

    public UsuarioItem() {
    }

    public UsuarioItem(int idUsuario, String nombreCompleto) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    @Override
    public String toString() {
        return nombreCompleto;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UsuarioItem)) return false;
        return idUsuario == ((UsuarioItem) obj).idUsuario;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idUsuario);
    }
}
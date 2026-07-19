package service;

import dao.EquipoDAO;
import modelo.Equipo;
import java.sql.SQLException;
import java.util.List;

public class EquipoService {

    private final EquipoDAO dao = new EquipoDAO();

    public void registrar(Equipo eq) throws SQLException {
        validar(eq);
        dao.registrar(eq);
    }

    public void actualizar(Equipo eq) throws SQLException {
        validar(eq);
        dao.actualizar(eq);
    }

    public void eliminar(int id) throws SQLException {
        dao.eliminar(id);
    }

    public List<Equipo> obtenerTodos() throws SQLException {
        return dao.listarTodos();
    }

    public Equipo obtenerPorId(int id) throws SQLException {
        return dao.obtenerPorId(id);
    }

    public int contar() throws SQLException {
        return dao.contar();
    }

    // Reglas de negocio: qué es un equipo "válido" antes de tocar la BD.
    // fechaAdquisicion y ubicacion quedan opcionales (a veces no se sabe al momento del alta).
    private void validar(Equipo eq) {
        if (eq.getTipo() == null || eq.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de equipo es obligatorio.");
        }
        if (eq.getMarca() == null || eq.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca es obligatoria.");
        }
        if (eq.getModelo() == null || eq.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo es obligatorio.");
        }
        if (eq.getNumeroSerie() == null || eq.getNumeroSerie().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de serie es obligatorio.");
        }
        if (eq.getEstado() == null || eq.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }
    }
}
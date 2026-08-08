package reparaciones.service;

import reparaciones.dao.ReparacionDAO;
import reparaciones.modelo.Reparacion;

import java.sql.SQLException;
import java.util.List;

public class ReparacionService {

    private final ReparacionDAO dao = new ReparacionDAO();

    public void registrar(Reparacion r) throws SQLException {
        validar(r);
        dao.registrar(r);
    }

    public void actualizar(Reparacion r) throws SQLException {
        if (r.getIdReparacion() <= 0) {
            throw new IllegalArgumentException("La reparación seleccionada no es válida.");
        }
        validar(r);
        dao.actualizar(r);
    }

    public void eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador de la reparación no es válido.");
        }
        dao.eliminar(id);
    }

    public List<Reparacion> obtenerTodos() throws SQLException {
        return dao.obtenerTodos();
    }

    public List<Reparacion> obtenerPorEquipo(int idEquipo) throws SQLException {
        return dao.obtenerPorEquipo(idEquipo);
    }

    public Reparacion obtenerPorId(int id) throws SQLException {
        return dao.obtenerPorId(id);
    }

    public int contar() throws SQLException {
        return dao.contar();
    }

    // Reglas de negocio: qué es una reparación "válida" antes de tocar la BD.
    // diagnostico, solucion y observaciones son opcionales en la tabla (NULL permitido).
    private void validar(Reparacion r) {
        if (r.getIdEquipo() <= 0) {
            throw new IllegalArgumentException("El equipo es obligatorio.");
        }
        if (r.getFechaIngreso() == null) {
            throw new IllegalArgumentException("La fecha de ingreso es obligatoria.");
        }
        if (r.getFallaReportada() == null || r.getFallaReportada().trim().isEmpty()) {
            throw new IllegalArgumentException("La falla reportada es obligatoria.");
        }
        if (r.getTecnico() == null || r.getTecnico().trim().isEmpty()) {
            throw new IllegalArgumentException("El técnico responsable es obligatorio.");
        }
        if (r.getEstado() == null || r.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }
        if (r.getCosto() < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }
        if (r.getFechaEntrega() != null && r.getFechaEntrega().before(r.getFechaIngreso())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser anterior a la de ingreso.");
        }
    }
}

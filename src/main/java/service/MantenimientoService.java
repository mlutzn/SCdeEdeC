package service;

import dao.MantenimientoDAO;
import modelo.Mantenimiento;
import java.sql.SQLException;
import java.util.List;

public class MantenimientoService {

    private MantenimientoDAO dao;

    public MantenimientoService() {
        this.dao = new MantenimientoDAO();
    }

    public void registrar(Mantenimiento m) throws SQLException {
        validar(m);
        dao.registrar(m);
    }

    public void actualizar(Mantenimiento m) throws SQLException {
        validar(m);
        dao.actualizar(m);
    }

    public List<Mantenimiento> obtenerTodos() throws SQLException {
        return dao.obtenerTodos();
    }

    public List<Mantenimiento> obtenerPorEquipo(int idEquipo) throws SQLException {
        return dao.obtenerPorEquipo(idEquipo);
    }

    public Mantenimiento obtenerPorId(int id) throws SQLException {
        return dao.obtenerPorId(id);
    }

    public void eliminar(int id) throws SQLException {
        dao.eliminar(id);
    }

    public int contar() throws SQLException {
        return dao.contar();
    }

    // Reglas de negocio: qué es un mantenimiento "válido" antes de tocar la BD
    private void validar(Mantenimiento m) {
        if (m.getIdEquipo() <= 0) {
            throw new IllegalArgumentException("El ID del equipo debe ser mayor a 0.");
        }
        if (m.getDescripcion() == null || m.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }
        if (m.getTecnico() == null || m.getTecnico().trim().isEmpty()) {
            throw new IllegalArgumentException("El técnico es obligatorio.");
        }
    }
}
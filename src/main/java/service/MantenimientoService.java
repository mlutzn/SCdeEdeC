package service;

import dao.MantenimientoDAO;
import modelo.Mantenimiento;
import java.util.List;

public class MantenimientoService {

    private MantenimientoDAO dao;

    public MantenimientoService() {
        this.dao = new MantenimientoDAO();
    }

    public boolean registrar(Mantenimiento m) {
        if (m.getIdEquipo() <= 0) {
            throw new IllegalArgumentException("El ID del equipo debe ser mayor a 0.");
        }
        if (m.getDescripcion() == null || m.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }
        if (m.getTecnico() == null || m.getTecnico().trim().isEmpty()) {
            throw new IllegalArgumentException("El técnico es obligatorio.");
        }
        return dao.registrar(m);
    }

    public List<Mantenimiento> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public List<Mantenimiento> obtenerPorEquipo(int idEquipo) {
        return dao.obtenerPorEquipo(idEquipo);
    }

    public Mantenimiento obtenerPorId(int id) {
        return dao.obtenerPorId(id);
    }

    public boolean actualizar(Mantenimiento m) {
        return dao.actualizar(m);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }

    public int contar() {
        return dao.contar();
    }
}
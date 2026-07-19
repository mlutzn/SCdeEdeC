package service;

import dao.EquipoDAO;
import modelo.EquipoComputo;

import java.sql.SQLException;
import java.util.List;

public class EquipoService {

    private final EquipoDAO equipoDAO;

    public EquipoService() {
        this.equipoDAO = new EquipoDAO();
    }

    public void registrar(EquipoComputo equipo) throws SQLException {
        validarEquipo(equipo);
        limpiarDatos(equipo);
        equipoDAO.registrar(equipo);
    }

    public void actualizar(EquipoComputo equipo) throws SQLException {

        if (equipo.getIdEquipo() <= 0) {
            throw new IllegalArgumentException(
                    "El equipo seleccionado no es válido."
            );
        }

        validarEquipo(equipo);
        limpiarDatos(equipo);
        equipoDAO.actualizar(equipo);
    }

    public void eliminar(int idEquipo) throws SQLException {

        if (idEquipo <= 0) {
            throw new IllegalArgumentException(
                    "El identificador del equipo no es válido."
            );
        }

        equipoDAO.eliminar(idEquipo);
    }

    public List<EquipoComputo> obtenerTodos() throws SQLException {
        return equipoDAO.listarTodos();
    }

    public EquipoComputo obtenerPorId(int idEquipo) throws SQLException {

        if (idEquipo <= 0) {
            throw new IllegalArgumentException(
                    "El identificador del equipo no es válido."
            );
        }

        return equipoDAO.obtenerPorId(idEquipo);
    }

    public List<EquipoComputo> buscar(String criterio) throws SQLException {

        if (criterio == null || criterio.trim().isEmpty()) {
            return equipoDAO.listarTodos();
        }

        return equipoDAO.buscar(criterio);
    }

    private void validarEquipo(EquipoComputo equipo) {

        if (equipo == null) {
            throw new IllegalArgumentException(
                    "Los datos del equipo son obligatorios."
            );
        }

        if (estaVacio(equipo.getTipo())) {
            throw new IllegalArgumentException(
                    "El tipo de equipo es obligatorio."
            );
        }

        if (estaVacio(equipo.getMarca())) {
            throw new IllegalArgumentException(
                    "La marca es obligatoria."
            );
        }

        if (estaVacio(equipo.getModelo())) {
            throw new IllegalArgumentException(
                    "El modelo es obligatorio."
            );
        }

        if (estaVacio(equipo.getNumeroSerie())) {
            throw new IllegalArgumentException(
                    "El número de serie es obligatorio."
            );
        }

        if (estaVacio(equipo.getEstado())) {
            throw new IllegalArgumentException(
                    "El estado del equipo es obligatorio."
            );
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private void limpiarDatos(EquipoComputo equipo) {
        equipo.setTipo(equipo.getTipo().trim());
        equipo.setMarca(equipo.getMarca().trim());
        equipo.setModelo(equipo.getModelo().trim());
        equipo.setNumeroSerie(equipo.getNumeroSerie().trim());
        equipo.setEstado(equipo.getEstado().trim());

        if (equipo.getUbicacion() != null) {
            equipo.setUbicacion(equipo.getUbicacion().trim());
        }
    }
}
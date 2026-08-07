package reportes.service;

import reportes.dao.ReporteDAO;
import reportes.modelo.Reporte;

import java.sql.SQLException;
import java.time.LocalDate;

public class ReporteService {
    private final ReporteDAO dao = new ReporteDAO();

    public Reporte obtenerInventarioPorEstado() throws SQLException {
        return dao.obtenerInventarioPorEstado();
    }

    public Reporte obtenerEquiposPorUsuario() throws SQLException {
        return dao.obtenerEquiposPorUsuario();
    }

    public Reporte obtenerMantenimientosPorEquipo() throws SQLException {
        return dao.obtenerMantenimientosPorEquipo();
    }

    public Reporte obtenerMantenimientosPorEquipo(LocalDate desde, LocalDate hasta) throws SQLException {
        validarRangoFechas(desde, hasta);
        return dao.obtenerMantenimientosPorEquipo(desde, hasta);
    }

    public Reporte obtenerReparacionesPorEquipo() throws SQLException {
        return dao.obtenerReparacionesPorEquipo();
    }

    public Reporte obtenerReparacionesPorEquipo(LocalDate desde, LocalDate hasta) throws SQLException {
        validarRangoFechas(desde, hasta);
        return dao.obtenerReparacionesPorEquipo(desde, hasta);
    }

    public Reporte obtenerResumenGeneral() throws SQLException {
        return dao.obtenerResumenGeneral();
    }

    private void validarRangoFechas(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Debe indicar ambas fechas (desde y hasta)");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
    }
}
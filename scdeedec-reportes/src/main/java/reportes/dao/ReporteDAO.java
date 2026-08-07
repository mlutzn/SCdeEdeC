package reportes.dao;

import reportes.modelo.Reporte;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ReporteDAO {

    /**
     * Motor genérico: ejecuta cualquier SELECT y arma un Reporte leyendo
     * los nombres de columna directamente del ResultSet (ResultSetMetaData),
     * así no hay que escribir un método distinto por cada estructura de datos.
     */
    private Reporte ejecutarConsulta(String sql, Object... parametros) throws SQLException {
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int totalColumnas = meta.getColumnCount();

                List<String> columnas = new ArrayList<>();
                for (int i = 1; i <= totalColumnas; i++) {
                    columnas.add(meta.getColumnLabel(i));
                }

                List<Object[]> filas = new ArrayList<>();
                while (rs.next()) {
                    Object[] fila = new Object[totalColumnas];
                    for (int i = 1; i <= totalColumnas; i++) {
                        fila[i - 1] = rs.getObject(i);
                    }
                    filas.add(fila);
                }

                return new Reporte(columnas, filas);
            }
        }
    }

    public Reporte obtenerInventarioPorEstado() throws SQLException {
        return ejecutarConsulta("SELECT * FROM vw_inventario_por_estado");
    }

    public Reporte obtenerEquiposPorUsuario() throws SQLException {
        return ejecutarConsulta("SELECT * FROM vw_equipos_por_usuario");
    }

    public Reporte obtenerMantenimientosPorEquipo() throws SQLException {
        return ejecutarConsulta("SELECT * FROM vw_mantenimientos_por_equipo");
    }

    public Reporte obtenerMantenimientosPorEquipo(LocalDate desde, LocalDate hasta) throws SQLException {
        return ejecutarConsulta(
                "SELECT * FROM vw_mantenimientos_por_equipo WHERE fecha BETWEEN ? AND ?",
                desde, hasta
        );
    }

    public Reporte obtenerReparacionesPorEquipo() throws SQLException {
        return ejecutarConsulta("SELECT * FROM vw_reparaciones_por_equipo");
    }

    public Reporte obtenerReparacionesPorEquipo(LocalDate desde, LocalDate hasta) throws SQLException {
        return ejecutarConsulta(
                "SELECT * FROM vw_reparaciones_por_equipo WHERE fechaIngreso BETWEEN ? AND ?",
                desde, hasta
        );
    }

    public Reporte obtenerResumenGeneral() throws SQLException {
        return ejecutarConsulta("SELECT * FROM vw_resumen_general");
    }
}
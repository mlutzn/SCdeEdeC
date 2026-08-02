package dashboard.dao;

import dashboard.modelo.ResumenDashboard;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    private static final String SQL_RESUMEN =
            "SELECT totalEquipos, totalUsuarios, " +
                    "totalMantenimientos, totalReparaciones, " +
                    "reparacionesPendientes " +
                    "FROM vw_resumen_general";

    public ResumenDashboard obtenerResumen() throws SQLException {

        ResumenDashboard resumen = new ResumenDashboard();

        try (
                Connection conexion = ConexionBD.getConnection();
                PreparedStatement sentencia =
                        conexion.prepareStatement(SQL_RESUMEN);
                ResultSet resultado = sentencia.executeQuery()
        ) {
            if (resultado.next()) {
                resumen.setTotalEquipos(
                        resultado.getInt("totalEquipos")
                );

                resumen.setTotalUsuarios(
                        resultado.getInt("totalUsuarios")
                );

                resumen.setTotalMantenimientos(
                        resultado.getInt("totalMantenimientos")
                );

                resumen.setTotalReparaciones(
                        resultado.getInt("totalReparaciones")
                );

                resumen.setReparacionesPendientes(
                        resultado.getInt("reparacionesPendientes")
                );
            }
        }

        return resumen;
    }
}
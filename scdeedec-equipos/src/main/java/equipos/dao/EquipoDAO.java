package equipos.dao;

import equipos.modelo.EquipoComputo;
import equipos.modelo.EquipoItem;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private static final String TABLA = "SCdeEdeC_Equipo";

    public void registrar(EquipoComputo equipo) throws SQLException {

        String sql = "INSERT INTO " + TABLA +
                " (tipo, marca, modelo, numeroSerie, fechaAdquisicion, estado, ubicacion)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conexion = ConexionBD.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            sentencia.setString(1, equipo.getTipo());
            sentencia.setString(2, equipo.getMarca());
            sentencia.setString(3, equipo.getModelo());
            sentencia.setString(4, equipo.getNumeroSerie());

            if (equipo.getFechaAdquisicion() != null) {
                sentencia.setDate(
                        5,
                        new java.sql.Date(
                                equipo.getFechaAdquisicion().getTime()
                        )
                );
            } else {
                sentencia.setNull(5, Types.DATE);
            }

            sentencia.setString(6, equipo.getEstado());
            sentencia.setString(7, equipo.getUbicacion());

            sentencia.executeUpdate();

            try (ResultSet resultado = sentencia.getGeneratedKeys()) {
                if (resultado.next()) {
                    equipo.setIdEquipo(resultado.getInt(1));
                }
            }
        }
    }

    public List<EquipoComputo> listarTodos() throws SQLException {

        List<EquipoComputo> equipos = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLA +
                " ORDER BY idEquipo DESC";

        try (
                Connection conexion = ConexionBD.getConnection();
                Statement sentencia = conexion.createStatement();
                ResultSet resultado = sentencia.executeQuery(sql)
        ) {
            while (resultado.next()) {
                equipos.add(convertirResultado(resultado));
            }
        }

        return equipos;
    }

    public EquipoComputo obtenerPorId(int idEquipo) throws SQLException {

        String sql = "SELECT * FROM " + TABLA +
                " WHERE idEquipo = ?";

        try (
                Connection conexion = ConexionBD.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {
            sentencia.setInt(1, idEquipo);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return convertirResultado(resultado);
                }
            }
        }

        return null;
    }

    public void actualizar(EquipoComputo equipo) throws SQLException {

        String sql = "UPDATE " + TABLA +
                " SET tipo = ?, marca = ?, modelo = ?, numeroSerie = ?," +
                " fechaAdquisicion = ?, estado = ?, ubicacion = ?" +
                " WHERE idEquipo = ?";

        try (
                Connection conexion = ConexionBD.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {
            sentencia.setString(1, equipo.getTipo());
            sentencia.setString(2, equipo.getMarca());
            sentencia.setString(3, equipo.getModelo());
            sentencia.setString(4, equipo.getNumeroSerie());

            if (equipo.getFechaAdquisicion() != null) {
                sentencia.setDate(
                        5,
                        new java.sql.Date(
                                equipo.getFechaAdquisicion().getTime()
                        )
                );
            } else {
                sentencia.setNull(5, Types.DATE);
            }

            sentencia.setString(6, equipo.getEstado());
            sentencia.setString(7, equipo.getUbicacion());
            sentencia.setInt(8, equipo.getIdEquipo());

            sentencia.executeUpdate();
        }
    }

    public void eliminar(int idEquipo) throws SQLException {

        String sql = "DELETE FROM " + TABLA +
                " WHERE idEquipo = ?";

        try (
                Connection conexion = ConexionBD.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {
            sentencia.setInt(1, idEquipo);
            sentencia.executeUpdate();
        }
    }

    public List<EquipoComputo> buscar(String criterio) throws SQLException {

        List<EquipoComputo> equipos = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLA +
                " WHERE tipo LIKE ?" +
                " OR marca LIKE ?" +
                " OR modelo LIKE ?" +
                " OR numeroSerie LIKE ?" +
                " OR estado LIKE ?" +
                " OR ubicacion LIKE ?" +
                " ORDER BY idEquipo DESC";

        String textoBusqueda = "%" + criterio.trim() + "%";

        try (
                Connection conexion = ConexionBD.getConnection();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {
            for (int posicion = 1; posicion <= 6; posicion++) {
                sentencia.setString(posicion, textoBusqueda);
            }

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    equipos.add(convertirResultado(resultado));
                }
            }
        }

        return equipos;
    }

    public List<EquipoItem> listarParaCombo() throws SQLException {

        List<EquipoItem> equipos = new ArrayList<>();

        String sql = "SELECT idEquipo, tipo, marca, modelo FROM " +
                TABLA + " ORDER BY tipo, marca";

        try (
                Connection conexion = ConexionBD.getConnection();
                Statement sentencia = conexion.createStatement();
                ResultSet resultado = sentencia.executeQuery(sql)
        ) {
            while (resultado.next()) {

                String descripcion =
                        resultado.getString("tipo") + " - " +
                                resultado.getString("marca") + " " +
                                resultado.getString("modelo");

                equipos.add(
                        new EquipoItem(
                                resultado.getInt("idEquipo"),
                                descripcion
                        )
                );
            }
        }

        return equipos;
    }

    private EquipoComputo convertirResultado(
            ResultSet resultado
    ) throws SQLException {

        EquipoComputo equipo = new EquipoComputo();

        equipo.setIdEquipo(resultado.getInt("idEquipo"));
        equipo.setTipo(resultado.getString("tipo"));
        equipo.setMarca(resultado.getString("marca"));
        equipo.setModelo(resultado.getString("modelo"));
        equipo.setNumeroSerie(resultado.getString("numeroSerie"));
        equipo.setFechaAdquisicion(
                resultado.getDate("fechaAdquisicion")
        );
        equipo.setEstado(resultado.getString("estado"));
        equipo.setUbicacion(resultado.getString("ubicacion"));

        return equipo;
    }
}
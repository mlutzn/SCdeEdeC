package dao;

import modelo.Equipo;
import modelo.EquipoItem;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    private static final String TABLA = "SCdeEdeC_Equipo";

    /**
     * Versión liviana para poblar combos (ej. en el formulario de Mantenimiento).
     */
    public List<EquipoItem> listarParaCombo() throws SQLException {
        List<EquipoItem> lista = new ArrayList<>();
        String sql = "SELECT idEquipo, tipo, marca, modelo FROM " + TABLA + " ORDER BY tipo, marca";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String desc = rs.getString("tipo") + " - " + rs.getString("marca") + " " + rs.getString("modelo");
                lista.add(new EquipoItem(rs.getInt("idEquipo"), desc));
            }
        }
        return lista;
    }

    public void registrar(Equipo eq) throws SQLException {
        String sql = "INSERT INTO " + TABLA +
                " (tipo, marca, modelo, numeroSerie, fechaAdquisicion, estado, ubicacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, eq.getTipo());
            pstmt.setString(2, eq.getMarca());
            pstmt.setString(3, eq.getModelo());
            pstmt.setString(4, eq.getNumeroSerie());
            if (eq.getFechaAdquisicion() != null) {
                pstmt.setDate(5, new java.sql.Date(eq.getFechaAdquisicion().getTime()));
            } else {
                pstmt.setNull(5, Types.DATE);
            }
            pstmt.setString(6, eq.getEstado());
            pstmt.setString(7, eq.getUbicacion());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    eq.setIdEquipo(rs.getInt(1));
                }
            }
        }
    }

    public List<Equipo> listarTodos() throws SQLException {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " ORDER BY idEquipo DESC";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearEquipo(rs));
            }
        }
        return lista;
    }

    public Equipo obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM " + TABLA + " WHERE idEquipo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEquipo(rs);
                }
            }
        }
        return null;
    }

    public void actualizar(Equipo eq) throws SQLException {
        String sql = "UPDATE " + TABLA +
                " SET tipo = ?, marca = ?, modelo = ?, numeroSerie = ?, fechaAdquisicion = ?, estado = ?, ubicacion = ? WHERE idEquipo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, eq.getTipo());
            pstmt.setString(2, eq.getMarca());
            pstmt.setString(3, eq.getModelo());
            pstmt.setString(4, eq.getNumeroSerie());
            if (eq.getFechaAdquisicion() != null) {
                pstmt.setDate(5, new java.sql.Date(eq.getFechaAdquisicion().getTime()));
            } else {
                pstmt.setNull(5, Types.DATE);
            }
            pstmt.setString(6, eq.getEstado());
            pstmt.setString(7, eq.getUbicacion());
            pstmt.setInt(8, eq.getIdEquipo());

            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLA + " WHERE idEquipo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + TABLA;

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private Equipo mapearEquipo(ResultSet rs) throws SQLException {
        Equipo eq = new Equipo();
        eq.setIdEquipo(rs.getInt("idEquipo"));
        eq.setTipo(rs.getString("tipo"));
        eq.setMarca(rs.getString("marca"));
        eq.setModelo(rs.getString("modelo"));
        eq.setNumeroSerie(rs.getString("numeroSerie"));
        eq.setFechaAdquisicion(rs.getDate("fechaAdquisicion"));
        eq.setEstado(rs.getString("estado"));
        eq.setUbicacion(rs.getString("ubicacion"));
        return eq;
    }
}
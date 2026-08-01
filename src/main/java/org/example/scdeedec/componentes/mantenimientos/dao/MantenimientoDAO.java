package org.example.scdeedec.componentes.mantenimientos.dao;

import org.example.scdeedec.componentes.mantenimientos.modelo.Mantenimiento;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {

    private static final String TABLA = "SCdeEdeC_Mantenimiento";

    public void registrar(Mantenimiento m) throws SQLException {
        String sql = "INSERT INTO " + TABLA + " (idEquipo, descripcion, fecha, tipo, tecnico, observaciones) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, m.getIdEquipo());
            pstmt.setString(2, m.getDescripcion());
            pstmt.setDate(3, new java.sql.Date(m.getFecha().getTime()));
            pstmt.setString(4, m.getTipo());
            pstmt.setString(5, m.getTecnico());
            pstmt.setString(6, m.getObservaciones());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setIdMantenimiento(rs.getInt(1));
                }
            }
        }
    }

    public List<Mantenimiento> obtenerTodos() throws SQLException {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearMantenimiento(rs));
            }
        }
        return lista;
    }

    public List<Mantenimiento> obtenerPorEquipo(int idEquipo) throws SQLException {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " WHERE idEquipo = ? ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEquipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearMantenimiento(rs));
                }
            }
        }
        return lista;
    }

    public Mantenimiento obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM " + TABLA + " WHERE idMantenimiento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearMantenimiento(rs);
                }
            }
        }
        return null;
    }

    public void actualizar(Mantenimiento m) throws SQLException {
        String sql = "UPDATE " + TABLA + " SET idEquipo = ?, descripcion = ?, fecha = ?, tipo = ?, tecnico = ?, observaciones = ? WHERE idMantenimiento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, m.getIdEquipo());
            pstmt.setString(2, m.getDescripcion());
            pstmt.setDate(3, new java.sql.Date(m.getFecha().getTime()));
            pstmt.setString(4, m.getTipo());
            pstmt.setString(5, m.getTecnico());
            pstmt.setString(6, m.getObservaciones());
            pstmt.setInt(7, m.getIdMantenimiento());

            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLA + " WHERE idMantenimiento = ?";

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

    private Mantenimiento mapearMantenimiento(ResultSet rs) throws SQLException {
        Mantenimiento m = new Mantenimiento();
        m.setIdMantenimiento(rs.getInt("idMantenimiento"));
        m.setIdEquipo(rs.getInt("idEquipo"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setFecha(rs.getDate("fecha"));
        m.setTipo(rs.getString("tipo"));
        m.setTecnico(rs.getString("tecnico"));
        m.setObservaciones(rs.getString("observaciones"));
        m.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
        return m;
    }
}
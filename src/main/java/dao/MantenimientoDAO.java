package dao;

import modelo.Mantenimiento;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAO {

    private static final String TABLA = "SCdeEdeC_mantenimientos";

    public boolean registrar(Mantenimiento m) {
        String sql = "INSERT INTO " + TABLA + " (id_equipo, descripcion, fecha, tipo, tecnico, observaciones) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, m.getIdEquipo());
            pstmt.setString(2, m.getDescripcion());
            pstmt.setDate(3, new java.sql.Date(m.getFecha().getTime()));
            pstmt.setString(4, m.getTipo());
            pstmt.setString(5, m.getTecnico());
            pstmt.setString(6, m.getObservaciones());

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    m.setIdMantenimiento(rs.getInt(1));
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Error al registrar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Mantenimiento> obtenerTodos() {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearMantenimiento(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public List<Mantenimiento> obtenerPorEquipo(int idEquipo) {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " WHERE id_equipo = ? ORDER BY fecha DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEquipo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearMantenimiento(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener por equipo: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public Mantenimiento obtenerPorId(int id) {
        String sql = "SELECT * FROM " + TABLA + " WHERE id_mantenimiento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapearMantenimiento(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizar(Mantenimiento m) {
        String sql = "UPDATE " + TABLA + " SET id_equipo = ?, descripcion = ?, fecha = ?, tipo = ?, tecnico = ?, observaciones = ? WHERE id_mantenimiento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, m.getIdEquipo());
            pstmt.setString(2, m.getDescripcion());
            pstmt.setDate(3, new java.sql.Date(m.getFecha().getTime()));
            pstmt.setString(4, m.getTipo());
            pstmt.setString(5, m.getTecnico());
            pstmt.setString(6, m.getObservaciones());
            pstmt.setInt(7, m.getIdMantenimiento());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM " + TABLA + " WHERE id_mantenimiento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM " + TABLA;

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al contar: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    private Mantenimiento mapearMantenimiento(ResultSet rs) throws SQLException {
        Mantenimiento m = new Mantenimiento();
        m.setIdMantenimiento(rs.getInt("id_mantenimiento"));
        m.setIdEquipo(rs.getInt("id_equipo"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setFecha(rs.getDate("fecha"));
        m.setTipo(rs.getString("tipo"));
        m.setTecnico(rs.getString("tecnico"));
        m.setObservaciones(rs.getString("observaciones"));
        m.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return m;
    }
}
package reparaciones.dao;

import reparaciones.modelo.Reparacion;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReparacionDAO {

    private static final String TABLA = "SCdeEdeC_Reparacion";

    public void registrar(Reparacion r) throws SQLException {
        String sql = "INSERT INTO " + TABLA +
                " (idEquipo, fechaIngreso, fallaReportada, diagnostico, solucion, tecnico, costo, estado, fechaEntrega, observaciones)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            asignarParametros(pstmt, r);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setIdReparacion(rs.getInt(1));
                }
            }
        }
    }

    public List<Reparacion> obtenerTodos() throws SQLException {
        List<Reparacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " ORDER BY fechaIngreso DESC";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearReparacion(rs));
            }
        }
        return lista;
    }

    public List<Reparacion> obtenerPorEquipo(int idEquipo) throws SQLException {
        List<Reparacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLA + " WHERE idEquipo = ? ORDER BY fechaIngreso DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEquipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReparacion(rs));
                }
            }
        }
        return lista;
    }

    public Reparacion obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM " + TABLA + " WHERE idReparacion = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearReparacion(rs);
                }
            }
        }
        return null;
    }

    public void actualizar(Reparacion r) throws SQLException {
        String sql = "UPDATE " + TABLA +
                " SET idEquipo = ?, fechaIngreso = ?, fallaReportada = ?, diagnostico = ?, solucion = ?," +
                " tecnico = ?, costo = ?, estado = ?, fechaEntrega = ?, observaciones = ? WHERE idReparacion = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int siguiente = asignarParametros(pstmt, r);
            pstmt.setInt(siguiente, r.getIdReparacion());

            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLA + " WHERE idReparacion = ?";

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

    /**
     * Carga los primeros 10 parámetros comunes a INSERT y UPDATE
     * (mismo orden de columnas en ambas sentencias). Devuelve el índice
     * del siguiente parámetro libre, para que UPDATE le agregue el WHERE id.
     */
    private int asignarParametros(PreparedStatement pstmt, Reparacion r) throws SQLException {
        pstmt.setInt(1, r.getIdEquipo());
        pstmt.setDate(2, new java.sql.Date(r.getFechaIngreso().getTime()));
        pstmt.setString(3, r.getFallaReportada());
        pstmt.setString(4, r.getDiagnostico());
        pstmt.setString(5, r.getSolucion());
        pstmt.setString(6, r.getTecnico());
        pstmt.setDouble(7, r.getCosto());
        pstmt.setString(8, r.getEstado());

        if (r.getFechaEntrega() != null) {
            pstmt.setDate(9, new java.sql.Date(r.getFechaEntrega().getTime()));
        } else {
            pstmt.setNull(9, Types.DATE);
        }

        pstmt.setString(10, r.getObservaciones());

        return 11;
    }

    private Reparacion mapearReparacion(ResultSet rs) throws SQLException {
        Reparacion r = new Reparacion();
        r.setIdReparacion(rs.getInt("idReparacion"));
        r.setIdEquipo(rs.getInt("idEquipo"));
        r.setFechaIngreso(rs.getDate("fechaIngreso"));
        r.setFallaReportada(rs.getString("fallaReportada"));
        r.setDiagnostico(rs.getString("diagnostico"));
        r.setSolucion(rs.getString("solucion"));
        r.setTecnico(rs.getString("tecnico"));
        r.setCosto(rs.getDouble("costo"));
        r.setEstado(rs.getString("estado"));
        r.setFechaEntrega(rs.getDate("fechaEntrega"));
        r.setObservaciones(rs.getString("observaciones"));
        r.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
        return r;
    }
}

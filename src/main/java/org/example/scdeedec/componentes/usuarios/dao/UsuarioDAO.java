package org.example.scdeedec.componentes.usuarios.dao;

/**
 * El DAO (Data Access Object) es la única clase que va a tener sentencias SQL escritas
 * "traductor" entre tu objeto Usuario de Java y las filas de la tabla SCdeEdeC_Usuario en MySQL.
 */


import org.example.scdeedec.componentes.usuarios.modelo.Usuario;
import util.ConexionBD;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.example.scdeedec.componentes.equipos.modelo.EquipoItem;
import org.example.scdeedec.componentes.usuarios.modelo.UsuarioItem;

public class UsuarioDAO {

    public void registrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO SCdeEdeC_Usuario (nombre, apellido, email, telefono, idEquipo) VALUES (?,?,?,?,?)";  //(signos de interrogación) como "espacios en blanco" que vamos a llenar después. Esto se llama PreparedStatement
        try (Connection con = ConexionBD.getConnection(); //ava cierra automáticamente el PreparedStatement (no la conexión completa, esa la maneja ConexionBD como singleton)
             PreparedStatement ps = con.prepareStatement(sql))
        {

            /**
             *  Llena el primer ? con el nombre del usuario.
             *  El número (1, 2, 3...) corresponde al orden de los ? en el SQL, no al orden de las columnas de la tabla.
             */

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());

            if (u.getIdEquipo() != null) //if/else
            {
                ps.setInt(5, u.getIdEquipo());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.executeUpdate();
        }
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();  // lista vacía donde vas a ir guardando cada usuario que encuentres.
        String sql = "SELECT * FROM SCdeEdeC_Usuario";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) //porque es un SELECT, que devuelve filas, no solo confirma que algo se guardó.
        {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setTelefono(rs.getString("telefono"));

                int idEquipo = rs.getInt("idEquipo");
                u.setIdEquipo(rs.wasNull() ? null : idEquipo);

                lista.add(u);
            }
        }
        return lista;
    }

    public void actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE SCdeEdeC_Usuario SET nombre=?, apellido=?, email=?, telefono=?, idEquipo=? WHERE idUsuario=?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());

            if (u.getIdEquipo() != null) {
                ps.setInt(5, u.getIdEquipo());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setInt(6, u.getIdUsuario()); //necesitas saber el ID del usuario que ya existe para poder editarlo.

            ps.executeUpdate();
        }
    }

    public void eliminar(int idUsuario) throws SQLException {
        String sql = "DELETE FROM SCdeEdeC_Usuario WHERE idUsuario=?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    public List<EquipoItem> listarEquiposDisponibles() throws SQLException {
        List<EquipoItem> lista = new ArrayList<>();
        String sql = "SELECT idEquipo, tipo, marca, modelo FROM SCdeEdeC_Equipo";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String desc = rs.getString("tipo") + " - " + rs.getString("marca") + " " + rs.getString("modelo");
                lista.add(new EquipoItem(rs.getInt("idEquipo"), desc));
            }
        }
        return lista;
    }

    /**
     * Versión liviana para poblar combos (ej. "Técnico" en el formulario de Mantenimiento).
     */
    public List<UsuarioItem> listarParaCombo() throws SQLException {
        List<UsuarioItem> lista = new ArrayList<>();
        String sql = "SELECT idUsuario, nombre, apellido FROM SCdeEdeC_Usuario ORDER BY nombre, apellido";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                lista.add(new UsuarioItem(rs.getInt("idUsuario"), nombreCompleto));
            }
        }
        return lista;
    }

}
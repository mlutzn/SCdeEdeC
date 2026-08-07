package usuarios.service;

/**
 * El DAO solo sabe hablar con la base de datos,
 * no le importa si un email tiene sentido o no, solo ejecuta el INSERT/UPDATE/DELETE que le pidas.
 * El Service es donde vive la "inteligencia del negocio"
 * antes de guardar cualquier cosa, revisa que los datos tengan sentido (validar()). Si algo está mal, lanza un
 * IllegalArgumentException con un mensaje claro, y ni siquiera llega a tocar la base de datos — corta el proceso ahí mismo.
 */

import usuarios.modelo.Usuario;
import usuarios.dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public void registrar(Usuario u) throws SQLException {
        validar(u);
        dao.registrar(u);
    }

    public void actualizar(Usuario u) throws SQLException {
        validar(u);
        dao.actualizar(u);
    }

    public void eliminar(int idUsuario) throws SQLException {
        dao.eliminar(idUsuario);
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        return dao.listarTodos();
    }

    // Reglas de negocio: acá decidimos qué es un usuario "válido"
    private void validar(Usuario u) {
        if (u.getNombre() == null || !u.getNombre().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,50}")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras");
        }
        if (u.getApellido() == null || !u.getApellido().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,50}")) {
            throw new IllegalArgumentException("El apellido solo debe contener letras");
        }
        if (u.getEmail() == null || !u.getEmail().contains("@")) {
            throw new IllegalArgumentException("El email no es válido");
        }
        if (u.getTelefono() == null || !u.getTelefono().matches("\\+?[0-9\\s\\-()]{7,20}")) {
            throw new IllegalArgumentException("El teléfono no es válido");
        }
    }
}
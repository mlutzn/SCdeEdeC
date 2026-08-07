package usuarios.controller;

//El Controller recibe la Vista por parámetro (no la crea él mismo)

import usuarios.modelo.Usuario;
import usuarios.service.UsuarioService;
import usuarios.vista.VistaUsuario;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import equipos.modelo.EquipoItem;
import usuarios.dao.UsuarioDAO;


public class UsuarioController {
    private final UsuarioService service = new UsuarioService();
    private final VistaUsuario vista;

    public UsuarioController(VistaUsuario vista) {
        this.vista = vista;
        inicializarEventos(); //un metodo que conecta cada botón de la Vista con lo que debe pasar al hacer clic.
        cargarEquipos(null);
        cargarTodos(); // apenas arranca, llena la tabla con los usuarios que ya existan en la BD
    }

    private void inicializarEventos() {
        vista.getBtnRegistrar().addActionListener(e -> registrar()); //una forma corta de decir "cuando hagan clic en este botón, ejecuta este metodo"
        vista.getBtnActualizar().addActionListener(e -> actualizar());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getBtnLimpiar().addActionListener(e -> vista.limpiarCampos());
        vista.getBtnConsultar().addActionListener(e -> cargarTodos());
        vista.getTablaUsuarios().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFilaSeleccionada();
            }
        });
    }

    //Los métodos que hacen el trabajo real

    private void registrar() {
        try {
            Usuario u = new Usuario(
                    vista.getTxtNombre().getText(),
                    vista.getTxtApellido().getText(),
                    vista.getTxtEmail().getText(),
                    vista.getTxtTelefono().getText(),
                    obtenerIdEquipoSeleccionado()
            );
            service.registrar(u);
            vista.mostrarExito("Usuario registrado correctamente");
            vista.limpiarCampos();
            cargarTodos();
            cargarEquipos(null);
        }

        catch (IllegalArgumentException ex)
        /**
         * Es el error que lanza tu UsuarioService.validar() cuando, por ejemplo, el email está vacío.
         * Aquí simplemente le muestras a la persona el mensaje que tú mismo escribiste ("El email no es válido").
         */
        {
            vista.mostrarError(ex.getMessage());
        }


        catch (SQLException ex)
        /**
         * Es un error real de la base de datos (por ejemplo, se cayó la conexión, o el email ya existe porque pusiste UNIQUE en la tabla).
         * Le muestras un mensaje distinto, porque la causa es otra.
         */
        {
            vista.mostrarError("Error de base de datos: " + ex.getMessage());
        }
    }

    private void actualizar() {
        try {
            int fila = vista.getTablaUsuarios().getSelectedRow(); // le pregunta a la tabla Swing "¿qué fila tiene seleccionada el usuario con el mouse?
            if (fila == -1) {
                vista.mostrarError("Selecciona un usuario de la tabla para actualizar");
                return;
            }

            int idUsuario = (int) vista.getTablaUsuarios().getValueAt(fila, 0); //lee el valor de esa fila, columna,Así sabes cuál usuario específico estás editando o borrando, no solo cuáles datos nuevos escribiste.

            Usuario u = new Usuario(
                    vista.getTxtNombre().getText(),
                    vista.getTxtApellido().getText(),
                    vista.getTxtEmail().getText(),
                    vista.getTxtTelefono().getText(),
                    obtenerIdEquipoSeleccionado()
            );
            u.setIdUsuario(idUsuario);

            service.actualizar(u);
            vista.mostrarExito("Usuario actualizado correctamente");
            vista.limpiarCampos();
            cargarTodos();
            cargarEquipos(null);
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            vista.mostrarError("Error de base de datos: " + ex.getMessage());
        }
    }

    private void eliminar() {
        try {
            int fila = vista.getTablaUsuarios().getSelectedRow();
            if (fila == -1) {
                vista.mostrarError("Selecciona un usuario de la tabla para eliminar");
                return;
            }

            int idUsuario = (int) vista.getTablaUsuarios().getValueAt(fila, 0);
            service.eliminar(idUsuario);
            vista.mostrarExito("Usuario eliminado correctamente");
            cargarTodos();
            cargarEquipos(null);
        } catch (SQLException ex) {
            vista.mostrarError("Error de base de datos: " + ex.getMessage());
        }
    }

    private void cargarTodos() {
        try {
            List<Usuario> usuarios = service.obtenerTodos();
            DefaultTableModel modelo = vista.getModeloTabla();
            modelo.setRowCount(0); // limpia la tabla antes de volver a llenarla

            for (Usuario u : usuarios) {
                modelo.addRow(new Object[] //agrega una fila nueva a la tabla, con un valor por columna, en el mismo orden en que definas las columnas de tu VistaUsuario
                        {
                                u.getIdUsuario(),
                                u.getNombre(),
                                u.getApellido(),
                                u.getEmail(),
                                u.getTelefono(),
                                u.getIdEquipo()
                        });
            }

            vista.getLblTotal().setText("Total: " + usuarios.size() + " usuarios");
        } catch (SQLException ex) {
            vista.mostrarError("Error al cargar usuarios: " + ex.getMessage());
        }
    }

    private void cargarEquipos(Integer idUsuarioActual) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<EquipoItem> equipos = dao.listarEquiposDisponibles(idUsuarioActual);
            vista.getCmbEquipo().removeAllItems();

            // idEquipo = 0 es un valor "sentinel": ningún equipo real tiene ese id (arrancan en 1),
            // así que lo usamos para representar "sin equipo asignado".
            vista.getCmbEquipo().addItem(new EquipoItem(0, "-- Ninguno --"));

            for (EquipoItem eq : equipos) {
                vista.getCmbEquipo().addItem(eq);
            }
            vista.getCmbEquipo().setSelectedIndex(0);
        } catch (SQLException ex) {
            vista.mostrarError("No se pudieron cargar los equipos: " + ex.getMessage());
        }
    }

    private Integer obtenerIdEquipoSeleccionado() {
        EquipoItem seleccionado = (EquipoItem) vista.getCmbEquipo().getSelectedItem();
        if (seleccionado == null || seleccionado.getIdEquipo() == 0) {
            return null;
        }
        return seleccionado.getIdEquipo();
    }

    private void cargarFilaSeleccionada() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila == -1) return;

        int idUsuario = (int) vista.getTablaUsuarios().getValueAt(fila, 0);

        vista.getTxtNombre().setText((String) vista.getTablaUsuarios().getValueAt(fila, 1));
        vista.getTxtApellido().setText((String) vista.getTablaUsuarios().getValueAt(fila, 2));
        vista.getTxtEmail().setText((String) vista.getTablaUsuarios().getValueAt(fila, 3));
        vista.getTxtTelefono().setText((String) vista.getTablaUsuarios().getValueAt(fila, 4));

        // Recarga el combo para este usuario: ve su propio equipo + los que estén libres
        cargarEquipos(idUsuario);

        Object idEquipoObj = vista.getTablaUsuarios().getValueAt(fila, 5);
        if (idEquipoObj != null) {
            for (int i = 0; i < vista.getCmbEquipo().getItemCount(); i++) {
                if (vista.getCmbEquipo().getItemAt(i).getIdEquipo() == (int) idEquipoObj) {
                    vista.getCmbEquipo().setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
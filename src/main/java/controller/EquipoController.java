package controller;

import modelo.Equipo;
import service.EquipoService;
import vista.VistaEquipo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EquipoController {

    private final VistaEquipo vista;
    private final EquipoService service;
    private final SimpleDateFormat sdf;

    public EquipoController(VistaEquipo vista) {
        this.vista = vista;
        this.service = new EquipoService();
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.sdf.setLenient(false);

        iniciarEventos();
        cargarTodos();
    }

    private void iniciarEventos() {
        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnActualizar().addActionListener(e -> actualizar());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getBtnLimpiar().addActionListener(e -> vista.limpiarCampos());
        vista.getBtnConsultar().addActionListener(e -> cargarTodos());

        vista.getTablaEquipos().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFilaSeleccionada();
            }
        });
    }

    private void registrar() {
        try {
            Equipo eq = leerFormulario();
            service.registrar(eq);
            vista.mostrarExito("✅ Equipo registrado correctamente.");
            vista.limpiarCampos();
            cargarTodos();
        } catch (IllegalArgumentException ex) {
            vista.mostrarError("❌ " + ex.getMessage());
        } catch (SQLException ex) {
            manejarErrorSQL(ex);
        }
    }

    private void actualizar() {
        int fila = vista.getTablaEquipos().getSelectedRow();
        if (fila == -1) {
            vista.mostrarError("Seleccione un equipo de la tabla para actualizar.");
            return;
        }

        try {
            int idEquipo = (int) vista.getTablaEquipos().getValueAt(fila, 0);
            Equipo eq = leerFormulario();
            eq.setIdEquipo(idEquipo);

            service.actualizar(eq);
            vista.mostrarExito("✅ Equipo actualizado correctamente.");
            vista.limpiarCampos();
            cargarTodos();
        } catch (IllegalArgumentException ex) {
            vista.mostrarError("❌ " + ex.getMessage());
        } catch (SQLException ex) {
            manejarErrorSQL(ex);
        }
    }

    private void eliminar() {
        int fila = vista.getTablaEquipos().getSelectedRow();
        if (fila == -1) {
            vista.mostrarError("Seleccione un equipo de la tabla para eliminar.");
            return;
        }

        int idEquipo = (int) vista.getTablaEquipos().getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar equipo ID: " + idEquipo + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                service.eliminar(idEquipo);
                vista.mostrarExito("✅ Equipo eliminado correctamente.");
                vista.limpiarCampos();
                cargarTodos();
            } catch (SQLException ex) {
                manejarErrorSQL(ex);
            }
        }
    }

    private void cargarTodos() {
        try {
            List<Equipo> equipos = service.obtenerTodos();
            DefaultTableModel modelo = vista.getModeloTabla();
            modelo.setRowCount(0);

            for (Equipo eq : equipos) {
                modelo.addRow(new Object[]{
                        eq.getIdEquipo(),
                        eq.getTipo(),
                        eq.getMarca(),
                        eq.getModelo(),
                        eq.getNumeroSerie(),
                        eq.getFechaAdquisicion() != null ? sdf.format(eq.getFechaAdquisicion()) : "",
                        eq.getEstado(),
                        eq.getUbicacion()
                });
            }

            vista.getLblTotal().setText("Total: " + equipos.size() + " equipos");
        } catch (SQLException ex) {
            manejarErrorSQL(ex);
        }
    }

    private void cargarFilaSeleccionada() {
        int fila = vista.getTablaEquipos().getSelectedRow();
        if (fila == -1) return;

        vista.getTxtTipo().setText((String) vista.getTablaEquipos().getValueAt(fila, 1));
        vista.getTxtMarca().setText((String) vista.getTablaEquipos().getValueAt(fila, 2));
        vista.getTxtModelo().setText((String) vista.getTablaEquipos().getValueAt(fila, 3));
        vista.getTxtNumeroSerie().setText((String) vista.getTablaEquipos().getValueAt(fila, 4));
        vista.getTxtFechaAdquisicion().setText((String) vista.getTablaEquipos().getValueAt(fila, 5));
        vista.getTxtUbicacion().setText((String) vista.getTablaEquipos().getValueAt(fila, 7));

        String estado = (String) vista.getTablaEquipos().getValueAt(fila, 6);
        vista.getCmbEstado().setSelectedItem(estado);
    }

    private Equipo leerFormulario() {
        String tipo = vista.getTxtTipo().getText().trim();
        String marca = vista.getTxtMarca().getText().trim();
        String modelo = vista.getTxtModelo().getText().trim();
        String numeroSerie = vista.getTxtNumeroSerie().getText().trim();
        String estado = (String) vista.getCmbEstado().getSelectedItem();
        String ubicacion = vista.getTxtUbicacion().getText().trim();
        String fechaTexto = vista.getTxtFechaAdquisicion().getText().trim();

        Date fecha = null;
        if (!fechaTexto.isEmpty()) {
            try {
                fecha = sdf.parse(fechaTexto);
            } catch (ParseException e) {
                throw new IllegalArgumentException("La fecha de adquisición debe tener formato dd/MM/yyyy.");
            }
        }

        return new Equipo(tipo, marca, modelo, numeroSerie, fecha, estado, ubicacion);
    }

    /**
     * Traduce las excepciones SQL más comunes a mensajes legibles.
     * 1451: intentaste borrar un equipo que tiene Usuarios o Mantenimientos asociados.
     * 1062: violación de UNIQUE (por ejemplo numeroSerie repetido, si le pusiste esa restricción).
     */
    private void manejarErrorSQL(SQLException e) {
        String mensaje;
        switch (e.getErrorCode()) {
            case 1451:
                mensaje = "⚠️ No se puede eliminar: hay usuarios o mantenimientos asociados a este equipo.";
                break;
            case 1062:
                mensaje = "⚠️ Ya existe un equipo con ese número de serie.";
                break;
            case 0:
                mensaje = "⚠️ No se pudo conectar a la base de datos. Verifique su conexión.";
                break;
            default:
                mensaje = "❌ Error de base de datos: " + e.getMessage();
        }
        vista.mostrarError(mensaje);
        e.printStackTrace();
    }
}
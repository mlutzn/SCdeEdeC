package controller;

import modelo.Mantenimiento;
import service.MantenimientoService;
import vista.VistaMantenimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MantenimientoController {

    private VistaMantenimiento vista;
    private MantenimientoService service;
    private SimpleDateFormat sdf;

    public MantenimientoController(VistaMantenimiento vista) {
        this.vista = vista;
        this.service = new MantenimientoService();
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");

        iniciarEventos();
        cargarTodos();
    }

    private void iniciarEventos() {
        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnConsultar().addActionListener(e -> cargarTodos());
        vista.getBtnLimpiar().addActionListener(e -> vista.limpiarCampos());
        vista.getBtnEliminar().addActionListener(e -> eliminar());
        vista.getBtnBuscarEquipo().addActionListener(e -> buscarPorEquipo());

        vista.getTxtIdBuscar().addActionListener(e -> {
            String texto = vista.getTxtIdBuscar().getText().trim();
            if (!texto.isEmpty()) {
                try {
                    buscarPorId(Integer.parseInt(texto));
                } catch (NumberFormatException ex) {
                    vista.mostrarError("El ID debe ser un número válido.");
                }
            }
        });
    }

    private void registrar() {
        try {
            int idEquipo = Integer.parseInt(vista.getTxtIdEquipo().getText().trim());
            String descripcion = vista.getTxtDescripcion().getText().trim();
            String tipo = (String) vista.getCmbTipo().getSelectedItem();
            String tecnico = vista.getTxtTecnico().getText().trim();
            String observaciones = vista.getTxtObservaciones().getText().trim();

            Mantenimiento m = new Mantenimiento();
            m.setIdEquipo(idEquipo);
            m.setDescripcion(descripcion);
            m.setFecha(new Date());
            m.setTipo(tipo);
            m.setTecnico(tecnico);
            m.setObservaciones(observaciones);

            service.registrar(m);
            vista.mostrarExito("✅ Mantenimiento registrado exitosamente.");
            vista.limpiarCampos();
            cargarTodos();

        } catch (NumberFormatException e) {
            vista.mostrarError("❌ El ID del equipo debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarError("❌ " + e.getMessage());
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void cargarTodos() {
        try {
            List<Mantenimiento> lista = service.obtenerTodos();
            mostrarEnTabla(lista);
            actualizarEstadisticas();
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void buscarPorEquipo() {
        try {
            String texto = vista.getTxtIdEquipo().getText().trim();
            if (texto.isEmpty()) {
                vista.mostrarError("Ingrese un ID de equipo.");
                return;
            }
            int idEquipo = Integer.parseInt(texto);
            List<Mantenimiento> lista = service.obtenerPorEquipo(idEquipo);
            mostrarEnTabla(lista);
            vista.mostrarMensaje("🔍 Se encontraron " + lista.size() + " mantenimientos.");
        } catch (NumberFormatException e) {
            vista.mostrarError("ID inválido.");
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void buscarPorId(int id) {
        try {
            Mantenimiento m = service.obtenerPorId(id);
            if (m != null) {
                DefaultTableModel model = vista.getModeloTabla();
                model.setRowCount(0);
                agregarFila(m);
                vista.mostrarMensaje("✅ Mantenimiento encontrado.");
            } else {
                vista.mostrarError("❌ No encontrado.");
            }
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void eliminar() {
        int fila = vista.getTablaMantenimientos().getSelectedRow();
        if (fila == -1) {
            vista.mostrarError("Seleccione un mantenimiento.");
            return;
        }

        int id = (int) vista.getModeloTabla().getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar mantenimiento ID: " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                service.eliminar(id);
                vista.mostrarExito("✅ Eliminado.");
                cargarTodos();
            } catch (SQLException e) {
                manejarErrorSQL(e);
            }
        }
    }

    private void mostrarEnTabla(List<Mantenimiento> lista) {
        DefaultTableModel model = vista.getModeloTabla();
        model.setRowCount(0);
        for (Mantenimiento m : lista) {
            agregarFila(m);
        }
    }

    private void agregarFila(Mantenimiento m) {
        DefaultTableModel model = vista.getModeloTabla();
        model.addRow(new Object[]{
                m.getIdMantenimiento(),
                m.getIdEquipo(),
                m.getDescripcion(),
                sdf.format(m.getFecha()),
                m.getTipo(),
                m.getTecnico(),
                m.getObservaciones()
        });
    }

    private void actualizarEstadisticas() {
        try {
            vista.getLblTotal().setText("📊 Total: " + service.contar() + " mantenimientos");
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    /**
     * Traduce las excepciones SQL más comunes a mensajes que un usuario
     * final entiende, en vez del texto crudo que devuelve MySQL/JDBC.
     */
    private void manejarErrorSQL(SQLException e) {
        String mensaje;
        switch (e.getErrorCode()) {
            case 1452: // Cannot add or update a child row: FK constraint fails
                mensaje = "⚠️ El equipo indicado no existe. Verificá el ID de equipo ingresado.";
                break;
            case 1451: // Cannot delete or update a parent row: FK constraint fails
                mensaje = "⚠️ No se puede eliminar: este registro tiene datos relacionados.";
                break;
            case 0: // Sin conexión / driver / timeout, no siempre trae código
                mensaje = "⚠️ No se pudo conectar a la base de datos. Verifique su conexión.";
                break;
            default:
                mensaje = "❌ Error de base de datos: " + e.getMessage();
        }
        vista.mostrarError(mensaje);
        e.printStackTrace(); // el detalle técnico completo queda en consola para debug
    }
}
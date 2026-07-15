package controller;

import modelo.Mantenimiento;
import service.MantenimientoService;
import vista.VistaMantenimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

            if (service.registrar(m)) {
                vista.mostrarExito("✅ Mantenimiento registrado exitosamente.");
                vista.limpiarCampos();
                cargarTodos();
            } else {
                vista.mostrarError("❌ Error al registrar el mantenimiento.");
            }

        } catch (NumberFormatException e) {
            vista.mostrarError("❌ El ID del equipo debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            vista.mostrarError("❌ " + e.getMessage());
        } catch (Exception e) {
            vista.mostrarError("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarTodos() {
        try {
            List<Mantenimiento> lista = service.obtenerTodos();
            mostrarEnTabla(lista);
            actualizarEstadisticas();
        } catch (Exception e) {
            vista.mostrarError("❌ Error al cargar: " + e.getMessage());
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
        }
    }

    private void buscarPorId(int id) {
        Mantenimiento m = service.obtenerPorId(id);
        if (m != null) {
            DefaultTableModel model = vista.getModeloTabla();
            model.setRowCount(0);
            agregarFila(m);
            vista.mostrarMensaje("✅ Mantenimiento encontrado.");
        } else {
            vista.mostrarError("❌ No encontrado.");
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
            if (service.eliminar(id)) {
                vista.mostrarExito("✅ Eliminado.");
                cargarTodos();
            } else {
                vista.mostrarError("❌ Error al eliminar.");
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
        vista.getLblTotal().setText("📊 Total: " + service.contar() + " mantenimientos");
    }
}
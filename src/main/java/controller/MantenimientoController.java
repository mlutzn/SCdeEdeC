package controller;

import dao.EquipoDAO;
import dao.UsuarioDAO;
import modelo.EquipoItem;
import modelo.Mantenimiento;
import modelo.UsuarioItem;
import service.MantenimientoService;
import vista.VistaMantenimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MantenimientoController {

    private VistaMantenimiento vista;
    private MantenimientoService service;
    private SimpleDateFormat sdf;

    // idEquipo -> "tipo - marca modelo", para mostrar el nombre en la tabla en vez del ID crudo
    private Map<Integer, String> mapaEquipos = new HashMap<>();

    public MantenimientoController(VistaMantenimiento vista) {
        this.vista = vista;
        this.service = new MantenimientoService();
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");

        iniciarEventos();
        cargarEquipos();
        cargarTecnicos();
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

    private void cargarEquipos() {
        try {
            List<EquipoItem> equipos = new EquipoDAO().listarParaCombo();

            vista.getCmbEquipo().removeAllItems();
            mapaEquipos.clear();

            for (EquipoItem eq : equipos) {
                vista.getCmbEquipo().addItem(eq);
                mapaEquipos.put(eq.getIdEquipo(), eq.getDescripcion());
            }
            vista.getCmbEquipo().setSelectedIndex(-1);
        } catch (SQLException ex) {
            vista.mostrarError("No se pudieron cargar los equipos: " + ex.getMessage());
        }
    }

    private void cargarTecnicos() {
        try {
            List<UsuarioItem> usuarios = new UsuarioDAO().listarParaCombo();
            vista.getCmbTecnico().removeAllItems();
            for (UsuarioItem u : usuarios) {
                vista.getCmbTecnico().addItem(u);
            }
            vista.getCmbTecnico().setSelectedIndex(-1);
        } catch (SQLException ex) {
            vista.mostrarError("No se pudieron cargar los técnicos: " + ex.getMessage());
        }
    }

    private void registrar() {
        try {
            EquipoItem equipoSel = (EquipoItem) vista.getCmbEquipo().getSelectedItem();
            UsuarioItem tecnicoSel = (UsuarioItem) vista.getCmbTecnico().getSelectedItem();

            if (equipoSel == null) {
                vista.mostrarError("❌ No hay equipos cargados. Registrá un equipo primero.");
                return;
            }
            if (tecnicoSel == null) {
                vista.mostrarError("❌ No hay usuarios cargados para asignar como técnico.");
                return;
            }

            String descripcion = vista.getTxtDescripcion().getText().trim();
            String tipo = (String) vista.getCmbTipo().getSelectedItem();
            String observaciones = vista.getTxtObservaciones().getText().trim();

            Mantenimiento m = new Mantenimiento();
            m.setIdEquipo(equipoSel.getIdEquipo());
            m.setDescripcion(descripcion);
            m.setFecha(new Date());
            m.setTipo(tipo);
            m.setTecnico(tecnicoSel.getNombreCompleto());
            m.setObservaciones(observaciones);

            service.registrar(m);
            vista.mostrarExito("✅ Mantenimiento registrado exitosamente.");
            vista.limpiarCampos();
            cargarTodos();

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
            EquipoItem equipoSel = (EquipoItem) vista.getCmbEquipo().getSelectedItem();
            if (equipoSel == null) {
                vista.mostrarError("No hay equipo seleccionado.");
                return;
            }
            List<Mantenimiento> lista = service.obtenerPorEquipo(equipoSel.getIdEquipo());
            mostrarEnTabla(lista);
            vista.mostrarMensaje("🔍 Se encontraron " + lista.size() + " mantenimientos.");
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
        String nombreEquipo = mapaEquipos.getOrDefault(m.getIdEquipo(), "ID " + m.getIdEquipo());

        model.addRow(new Object[]{
                m.getIdMantenimiento(),
                nombreEquipo,
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

    private void manejarErrorSQL(SQLException e) {
        String mensaje;
        switch (e.getErrorCode()) {
            case 1452:
                mensaje = "⚠️ El equipo indicado no existe. Verificá el equipo seleccionado.";
                break;
            case 1451:
                mensaje = "⚠️ No se puede eliminar: este registro tiene datos relacionados.";
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
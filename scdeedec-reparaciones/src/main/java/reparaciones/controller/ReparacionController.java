package reparaciones.controller;

import equipos.dao.EquipoDAO;
import equipos.modelo.EquipoItem;

import usuarios.dao.UsuarioDAO;
import usuarios.modelo.UsuarioItem;

import reparaciones.modelo.Reparacion;
import reparaciones.service.ReparacionService;
import reparaciones.vista.VistaReparacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReparacionController {

    private final VistaReparacion vista;
    private final ReparacionService service;
    private final SimpleDateFormat sdf;

    // idEquipo -> "tipo - marca modelo", para mostrar el nombre en la tabla en vez del ID crudo
    private final Map<Integer, String> mapaEquipos = new HashMap<>();

    public ReparacionController(VistaReparacion vista) {
        this.vista = vista;
        this.service = new ReparacionService();
        this.sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        this.sdf.setLenient(false);

        iniciarEventos();
        cargarEquipos();
        cargarTecnicos();
        cargarTodos();
    }

    private void iniciarEventos() {
        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnActualizar().addActionListener(e -> actualizar());
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

        vista.getTablaReparaciones().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFilaSeleccionada();
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
            Reparacion r = leerFormulario();
            service.registrar(r);
            vista.mostrarExito("✅ Reparación registrada exitosamente.");
            vista.limpiarCampos();
            cargarTodos();
        } catch (IllegalArgumentException e) {
            vista.mostrarError("❌ " + e.getMessage());
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void actualizar() {
        int fila = vista.getTablaReparaciones().getSelectedRow();
        if (fila == -1) {
            vista.mostrarError("Seleccione una reparación de la tabla para actualizar.");
            return;
        }

        try {
            int idReparacion = (int) vista.getTablaReparaciones().getValueAt(fila, 0);

            Reparacion r = leerFormulario();
            r.setIdReparacion(idReparacion);

            service.actualizar(r);
            vista.mostrarExito("✅ Reparación actualizada correctamente.");
            vista.limpiarCampos();
            cargarTodos();
        } catch (IllegalArgumentException e) {
            vista.mostrarError("❌ " + e.getMessage());
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void eliminar() {
        int fila = vista.getTablaReparaciones().getSelectedRow();
        if (fila == -1) {
            vista.mostrarError("Seleccione una reparación.");
            return;
        }

        int id = (int) vista.getModeloTabla().getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar reparación ID: " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                service.eliminar(id);
                vista.mostrarExito("✅ Eliminada.");
                vista.limpiarCampos();
                cargarTodos();
            } catch (SQLException e) {
                manejarErrorSQL(e);
            }
        }
    }

    private void cargarTodos() {
        try {
            List<Reparacion> lista = service.obtenerTodos();
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
            List<Reparacion> lista = service.obtenerPorEquipo(equipoSel.getIdEquipo());
            mostrarEnTabla(lista);
            vista.mostrarMensaje("🔍 Se encontraron " + lista.size() + " reparaciones.");
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void buscarPorId(int id) {
        try {
            Reparacion r = service.obtenerPorId(id);
            if (r != null) {
                DefaultTableModel model = vista.getModeloTabla();
                model.setRowCount(0);
                agregarFila(r);
                vista.mostrarMensaje("✅ Reparación encontrada.");
            } else {
                vista.mostrarError("❌ No encontrada.");
            }
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void mostrarEnTabla(List<Reparacion> lista) {
        DefaultTableModel model = vista.getModeloTabla();
        model.setRowCount(0);
        for (Reparacion r : lista) {
            agregarFila(r);
        }
    }

    private void agregarFila(Reparacion r) {
        DefaultTableModel model = vista.getModeloTabla();
        String nombreEquipo = mapaEquipos.getOrDefault(r.getIdEquipo(), "ID " + r.getIdEquipo());
        String fechaEntregaTexto = r.getFechaEntrega() != null ? sdf.format(r.getFechaEntrega()) : "";

        model.addRow(new Object[]{
                r.getIdReparacion(),
                nombreEquipo,
                r.getFallaReportada(),
                sdf.format(r.getFechaIngreso()),
                fechaEntregaTexto,
                r.getEstado(),
                String.format(Locale.getDefault(), "%.2f", r.getCosto()),
                r.getTecnico()
        });
    }

    /**
     * A diferencia de Mantenimiento, acá no alcanza con leer las columnas de la
     * tabla: diagnóstico, solución y observaciones no se muestran en la grilla.
     * Por eso se vuelve a pedir el registro completo por ID al Service.
     */
    private void cargarFilaSeleccionada() {
        int fila = vista.getTablaReparaciones().getSelectedRow();
        if (fila == -1) return;

        int idReparacion = (int) vista.getModeloTabla().getValueAt(fila, 0);

        try {
            Reparacion r = service.obtenerPorId(idReparacion);
            if (r == null) return;

            seleccionarEquipoPorId(r.getIdEquipo());
            seleccionarTecnicoPorNombre(r.getTecnico());

            vista.getTxtFallaReportada().setText(r.getFallaReportada());
            vista.getTxtDiagnostico().setText(r.getDiagnostico() != null ? r.getDiagnostico() : "");
            vista.getTxtSolucion().setText(r.getSolucion() != null ? r.getSolucion() : "");
            vista.getTxtObservaciones().setText(r.getObservaciones() != null ? r.getObservaciones() : "");

            vista.getTxtFechaIngreso().setFecha(r.getFechaIngreso());
            vista.getTxtFechaEntrega().setFecha(r.getFechaEntrega());

            vista.getCmbEstado().setSelectedItem(r.getEstado());
            vista.getTxtCosto().setText(String.format(Locale.getDefault(), "%.2f", r.getCosto()));

        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void seleccionarEquipoPorId(int idEquipo) {
        JComboBox<EquipoItem> combo = vista.getCmbEquipo();
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).getIdEquipo() == idEquipo) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(-1);
    }

    private void seleccionarTecnicoPorNombre(String nombreCompleto) {
        JComboBox<UsuarioItem> combo = vista.getCmbTecnico();
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).getNombreCompleto().equals(nombreCompleto)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(-1);
    }

    private void actualizarEstadisticas() {
        try {
            vista.getLblTotal().setText("📊 Total: " + service.contar() + " reparaciones");
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private Reparacion leerFormulario() {
        EquipoItem equipoSel = (EquipoItem) vista.getCmbEquipo().getSelectedItem();
        UsuarioItem tecnicoSel = (UsuarioItem) vista.getCmbTecnico().getSelectedItem();

        if (equipoSel == null) {
            throw new IllegalArgumentException("No hay equipos cargados. Registrá un equipo primero.");
        }
        if (tecnicoSel == null) {
            throw new IllegalArgumentException("No hay usuarios cargados para asignar como técnico.");
        }

        String fallaReportada = vista.getTxtFallaReportada().getText().trim();
        String diagnostico = vacioComoNull(vista.getTxtDiagnostico().getText());
        String solucion = vacioComoNull(vista.getTxtSolucion().getText());
        String observaciones = vacioComoNull(vista.getTxtObservaciones().getText());
        String estado = (String) vista.getCmbEstado().getSelectedItem();

        Date fechaIngreso = parsearFecha(vista.getTxtFechaIngreso().getText().trim(), true);
        Date fechaEntrega = parsearFecha(vista.getTxtFechaEntrega().getText().trim(), false);

        double costo = parsearCosto(vista.getTxtCosto().getText().trim());

        return new Reparacion(
                equipoSel.getIdEquipo(),
                fechaIngreso,
                fallaReportada,
                diagnostico,
                solucion,
                tecnicoSel.getNombreCompleto(),
                costo,
                estado,
                fechaEntrega,
                observaciones
        );
    }

    private String vacioComoNull(String texto) {
        if (texto == null) return null;
        String limpio = texto.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    private Date parsearFecha(String texto, boolean obligatoria) {
        if (texto.isEmpty()) {
            if (obligatoria) {
                throw new IllegalArgumentException("La fecha de ingreso es obligatoria y debe tener formato dd/MM/yyyy.");
            }
            return null;
        }
        try {
            return sdf.parse(texto);
        } catch (ParseException e) {
            throw new IllegalArgumentException("La fecha debe tener formato dd/MM/yyyy.");
        }
    }

    private double parsearCosto(String texto) {
        if (texto.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(texto.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El costo debe ser un número válido (ej. 45.50).");
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

package org.example.scdeedec.componentes.equipos.controller;

import org.example.scdeedec.componentes.equipos.modelo.EquipoComputo;
import org.example.scdeedec.componentes.equipos.service.EquipoService;
import org.example.scdeedec.componentes.equipos.vista.VistaEquipo;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EquipoController {

    private final VistaEquipo vista;
    private final EquipoService service;
    private final SimpleDateFormat formatoFecha;

    public EquipoController(VistaEquipo vista) {
        this.vista = vista;
        this.service = new EquipoService();

        this.formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        this.formatoFecha.setLenient(false);

        iniciarEventos();
        cargarTodos();
    }

    private void iniciarEventos() {

        vista.getBtnRegistrar().addActionListener(
                e -> registrar()
        );

        vista.getBtnActualizar().addActionListener(
                e -> actualizar()
        );

        vista.getBtnEliminar().addActionListener(
                e -> eliminar()
        );

        vista.getBtnLimpiar().addActionListener(
                e -> vista.limpiarCampos()
        );

        vista.getBtnConsultar().addActionListener(
                e -> cargarTodos()
        );

        vista.getBtnBuscar().addActionListener(
                e -> buscar()
        );

        vista.getTxtBuscar().addActionListener(
                e -> buscar()
        );

        vista.getTablaEquipos()
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        cargarFilaSeleccionada();
                    }
                });
    }

    private void registrar() {

        try {
            EquipoComputo equipo = leerFormulario();

            service.registrar(equipo);

            vista.mostrarExito(
                    "Equipo registrado correctamente."
            );

            vista.limpiarCampos();
            cargarTodos();

        } catch (IllegalArgumentException error) {

            vista.mostrarError(error.getMessage());

        } catch (SQLException error) {

            manejarErrorSQL(error);
        }
    }

    private void actualizar() {

        int filaSeleccionada =
                vista.getTablaEquipos().getSelectedRow();

        if (filaSeleccionada == -1) {
            vista.mostrarError(
                    "Seleccione un equipo de la tabla para actualizar."
            );
            return;
        }

        try {
            int idEquipo = (int) vista
                    .getTablaEquipos()
                    .getValueAt(filaSeleccionada, 0);

            EquipoComputo equipo = leerFormulario();
            equipo.setIdEquipo(idEquipo);

            service.actualizar(equipo);

            vista.mostrarExito(
                    "Equipo actualizado correctamente."
            );

            vista.limpiarCampos();
            cargarTodos();

        } catch (IllegalArgumentException error) {

            vista.mostrarError(error.getMessage());

        } catch (SQLException error) {

            manejarErrorSQL(error);
        }
    }

    private void eliminar() {

        int filaSeleccionada =
                vista.getTablaEquipos().getSelectedRow();

        if (filaSeleccionada == -1) {
            vista.mostrarError(
                    "Seleccione un equipo de la tabla para eliminar."
            );
            return;
        }

        int idEquipo = (int) vista
                .getTablaEquipos()
                .getValueAt(filaSeleccionada, 0);

        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea eliminar el equipo seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            service.eliminar(idEquipo);

            vista.mostrarExito(
                    "Equipo eliminado correctamente."
            );

            vista.limpiarCampos();
            cargarTodos();

        } catch (IllegalArgumentException error) {

            vista.mostrarError(error.getMessage());

        } catch (SQLException error) {

            manejarErrorSQL(error);
        }
    }

    private void cargarTodos() {

        try {
            List<EquipoComputo> equipos =
                    service.obtenerTodos();

            mostrarEquipos(equipos);

        } catch (SQLException error) {

            manejarErrorSQL(error);
        }
    }

    private void buscar() {

        try {
            String criterio =
                    vista.getTxtBuscar().getText();

            List<EquipoComputo> equipos =
                    service.buscar(criterio);

            mostrarEquipos(equipos);

        } catch (SQLException error) {

            manejarErrorSQL(error);
        }
    }

    private void mostrarEquipos(
            List<EquipoComputo> equipos
    ) {
        DefaultTableModel modeloTabla =
                vista.getModeloTabla();

        modeloTabla.setRowCount(0);

        for (EquipoComputo equipo : equipos) {

            String fecha = "";

            if (equipo.getFechaAdquisicion() != null) {
                fecha = formatoFecha.format(
                        equipo.getFechaAdquisicion()
                );
            }

            modeloTabla.addRow(new Object[]{
                    equipo.getIdEquipo(),
                    equipo.getTipo(),
                    equipo.getMarca(),
                    equipo.getModelo(),
                    equipo.getNumeroSerie(),
                    fecha,
                    equipo.getEstado(),
                    equipo.getUbicacion()
            });
        }

        vista.getLblTotal().setText(
                "Total: " + equipos.size() + " equipos"
        );
    }

    private void cargarFilaSeleccionada() {

        int filaSeleccionada =
                vista.getTablaEquipos().getSelectedRow();

        if (filaSeleccionada == -1) {
            return;
        }

        vista.getTxtTipo().setText(
                obtenerTextoTabla(filaSeleccionada, 1)
        );

        vista.getTxtMarca().setText(
                obtenerTextoTabla(filaSeleccionada, 2)
        );

        vista.getTxtModelo().setText(
                obtenerTextoTabla(filaSeleccionada, 3)
        );

        vista.getTxtNumeroSerie().setText(
                obtenerTextoTabla(filaSeleccionada, 4)
        );

        vista.getTxtFechaAdquisicion().setText(
                obtenerTextoTabla(filaSeleccionada, 5)
        );

        vista.getCmbEstado().setSelectedItem(
                obtenerTextoTabla(filaSeleccionada, 6)
        );

        vista.getTxtUbicacion().setText(
                obtenerTextoTabla(filaSeleccionada, 7)
        );
    }

    private String obtenerTextoTabla(
            int fila,
            int columna
    ) {
        Object valor = vista
                .getTablaEquipos()
                .getValueAt(fila, columna);

        return valor == null ? "" : valor.toString();
    }

    private EquipoComputo leerFormulario() {

        String tipo =
                vista.getTxtTipo().getText();

        String marca =
                vista.getTxtMarca().getText();

        String modelo =
                vista.getTxtModelo().getText();

        String numeroSerie =
                vista.getTxtNumeroSerie().getText();

        String estado =
                (String) vista.getCmbEstado().getSelectedItem();

        String ubicacion =
                vista.getTxtUbicacion().getText();

        String fechaTexto =
                vista.getTxtFechaAdquisicion()
                        .getText()
                        .trim();

        Date fechaAdquisicion = null;

        if (!fechaTexto.isEmpty()) {
            try {
                fechaAdquisicion =
                        formatoFecha.parse(fechaTexto);

            } catch (ParseException error) {

                throw new IllegalArgumentException(
                        "La fecha debe tener el formato dd/MM/yyyy."
                );
            }
        }

        return new EquipoComputo(
                tipo,
                marca,
                modelo,
                numeroSerie,
                fechaAdquisicion,
                estado,
                ubicacion
        );
    }

    private void manejarErrorSQL(SQLException error) {

        String mensaje;

        switch (error.getErrorCode()) {

            case 1062:
                mensaje =
                        "Ya existe un equipo con ese número de serie.";
                break;

            case 1451:
                mensaje =
                        "No se puede eliminar el equipo porque tiene usuarios o mantenimientos asociados.";
                break;

            default:
                mensaje =
                        "Error de base de datos: " +
                                error.getMessage();
        }

        vista.mostrarError(mensaje);
        error.printStackTrace();
    }
}
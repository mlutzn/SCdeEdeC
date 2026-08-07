package dashboard.controller;

import dashboard.modelo.ResumenDashboard;
import dashboard.service.DashboardService;

import equipos.controller.EquipoController;
import equipos.vista.VistaEquipo;

import mantenimientos.controller.MantenimientoController;
import mantenimientos.vista.VistaMantenimiento;

import usuarios.controller.UsuarioController;
import usuarios.vista.VistaUsuario;

import vista.VistaDashboard;

import javax.swing.*;
import java.sql.SQLException;

import reportes.controller.ReporteController;
import reportes.vista.VistaReporte;

public class DashboardController {

    private final VistaDashboard vista;
    private final DashboardService service;

    public DashboardController(VistaDashboard vista) {
        this.vista = vista;
        this.service = new DashboardService();

        iniciarEventos();
        cargarResumen();
    }

    private void iniciarEventos() {

        vista.getBtnActualizarResumen()
                .addActionListener(
                        e -> cargarResumen()
                );

        vista.getBtnEquipos()
                .addActionListener(
                        e -> abrirEquipos()
                );

        vista.getBtnUsuarios()
                .addActionListener(
                        e -> abrirUsuarios()
                );

        vista.getBtnMantenimientos()
                .addActionListener(
                        e -> abrirMantenimientos()
                );

        vista.getBtnReparaciones()
                .addActionListener(
                        e -> mostrarModuloEnDesarrollo(
                                "Reparaciones"
                        )
                );

        vista.getBtnReportes()
                .addActionListener(
                        e -> abrirReportes()
                );

        vista.getBtnSalir()
                .addActionListener(
                        e -> salir()
                );
    }

    private void cargarResumen() {

        try {
            ResumenDashboard resumen =
                    service.obtenerResumen();

            vista.mostrarResumen(resumen);

        } catch (SQLException error) {

            vista.mostrarError(
                    "No se pudo cargar el resumen.\n" +
                            "Verifique que la vista SQL " +
                            "vw_resumen_general exista.\n\n" +
                            error.getMessage()
            );

            error.printStackTrace();
        }
    }

    private void abrirEquipos() {

        VistaEquipo vistaEquipo =
                new VistaEquipo();

        new EquipoController(vistaEquipo);
    }

    private void abrirUsuarios() {

        VistaUsuario vistaUsuario =
                new VistaUsuario();

        new UsuarioController(vistaUsuario);
    }

    private void abrirMantenimientos() {

        VistaMantenimiento vistaMantenimiento =
                new VistaMantenimiento();

        new MantenimientoController(
                vistaMantenimiento
        );
    }

    private void abrirReportes() {

        VistaReporte vistaReporte =
                new VistaReporte();

        new ReporteController(vistaReporte);
    }

    private void mostrarModuloEnDesarrollo(
            String nombreModulo
    ) {
        JOptionPane.showMessageDialog(
                vista,
                "El módulo de " + nombreModulo +
                        " está preparado y pendiente " +
                        "de completar su funcionalidad.",
                nombreModulo,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void salir() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        vista,
                        "¿Desea cerrar el sistema?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
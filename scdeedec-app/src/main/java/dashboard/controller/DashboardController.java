package dashboard.controller;

import dashboard.modelo.ResumenDashboard;
import dashboard.service.DashboardService;

import equipos.controller.EquipoController;
import equipos.vista.VistaEquipo;

import mantenimientos.controller.MantenimientoController;
import mantenimientos.vista.VistaMantenimiento;

import reparaciones.controller.ReparacionController;
import reparaciones.vista.VistaReparacion;

import usuarios.controller.UsuarioController;
import usuarios.vista.VistaUsuario;

import vista.VistaDashboard;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import reportes.controller.ReporteController;
import reportes.vista.VistaReporte;

public class DashboardController {

    private final VistaDashboard vista;
    private final DashboardService service;

    private VistaEquipo vistaEquipoAbierta;
    private VistaUsuario vistaUsuarioAbierta;
    private VistaMantenimiento vistaMantenimientoAbierta;
    private VistaReparacion vistaReparacionAbierta;
    private VistaReporte vistaReporteAbierta;

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
                        e -> abrirReparaciones()
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
        if (traerAlFrente(vistaEquipoAbierta)) return;

        VistaEquipo vistaEquipo = new VistaEquipo();
        new EquipoController(vistaEquipo);

        vistaEquipoAbierta = vistaEquipo;
        vistaEquipo.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                vistaEquipoAbierta = null;
            }
        });
    }

    private void abrirUsuarios() {
        if (traerAlFrente(vistaUsuarioAbierta)) return;

        VistaUsuario vistaUsuario = new VistaUsuario();
        new UsuarioController(vistaUsuario);

        vistaUsuarioAbierta = vistaUsuario;
        vistaUsuario.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                vistaUsuarioAbierta = null;
            }
        });
    }

    private void abrirMantenimientos() {
        if (traerAlFrente(vistaMantenimientoAbierta)) return;

        VistaMantenimiento vistaMantenimiento = new VistaMantenimiento();
        new MantenimientoController(vistaMantenimiento);

        vistaMantenimientoAbierta = vistaMantenimiento;
        vistaMantenimiento.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                vistaMantenimientoAbierta = null;
            }
        });
    }

    private void abrirReparaciones() {
        if (traerAlFrente(vistaReparacionAbierta)) return;

        VistaReparacion vistaReparacion = new VistaReparacion();
        new ReparacionController(vistaReparacion);

        vistaReparacionAbierta = vistaReparacion;
        vistaReparacion.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                vistaReparacionAbierta = null;
            }
        });
    }

    private void abrirReportes() {
        if (traerAlFrente(vistaReporteAbierta)) return;

        VistaReporte vistaReporte = new VistaReporte();
        new ReporteController(vistaReporte);

        vistaReporteAbierta = vistaReporte;
        vistaReporte.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                vistaReporteAbierta = null;
            }
        });
    }

    /**
     * Si la ventana ya está abierta (no fue cerrada/descartada), la trae al
     * frente y le da foco en vez de abrir una segunda instancia del mismo
     * módulo. Devuelve true si reutilizó una ventana existente.
     */
    private boolean traerAlFrente(JFrame ventana) {
        if (ventana == null || !ventana.isDisplayable()) {
            return false;
        }

        if (ventana.getExtendedState() == JFrame.ICONIFIED) {
            ventana.setExtendedState(JFrame.NORMAL);
        }

        ventana.toFront();
        ventana.requestFocus();
        return true;
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
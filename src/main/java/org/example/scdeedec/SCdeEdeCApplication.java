package org.example.scdeedec;

import util.ConexionBD;
import vista.VistaMantenimiento;
import controller.MantenimientoController;

import javax.swing.*;

public class SCdeEdeC {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                if (!ConexionBD.testConexion()) {
                    JOptionPane.showMessageDialog(null,
                            "❌ No se pudo conectar a la base de datos.\nVerifique MySQL.",
                            "Error de Conexión",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                VistaMantenimiento vista = new VistaMantenimiento();
                new MantenimientoController(vista);

                System.out.println("🚀 Sistema de Mantenimientos iniciado!");
                System.out.println("📌 Base de datos: sistema_equipos");
                System.out.println("📌 Tabla: SCdeEdeC_mantenimientos");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "❌ Error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
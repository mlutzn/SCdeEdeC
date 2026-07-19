package org.example.scdeedec;

import util.ConexionBD;
import vista.VistaPrincipal;

import javax.swing.*;

public class SCdeEdeCApplication {
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

                if (!ConexionBD.inicializarTablas()) {
                    JOptionPane.showMessageDialog(null,
                            "❌ No se pudieron crear/verificar las tablas.",
                            "Error de Inicialización",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                new VistaPrincipal();

                System.out.println("🚀 Sistema de Control de Equipos iniciado!");
                System.out.println("📌 Base de datos: sistema_equipos");

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
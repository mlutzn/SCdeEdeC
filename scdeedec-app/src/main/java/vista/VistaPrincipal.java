package vista;

import dashboard.controller.DashboardController;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    public VistaPrincipal() {

        setTitle("Sistema de Control de Equipos");

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setMinimumSize(
                new Dimension(1000, 650)
        );

        setSize(1180, 720);
        setLocationRelativeTo(null);
        setResizable(true);

        VistaDashboard vistaDashboard =
                new VistaDashboard();

        setContentPane(vistaDashboard);

        setVisible(true);

        new DashboardController(vistaDashboard);
    }
}
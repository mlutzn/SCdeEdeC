package vista;

import org.example.scdeedec.componentes.equipos.controller.EquipoController;
import org.example.scdeedec.componentes.equipos.vista.VistaEquipo;
import org.example.scdeedec.componentes.mantenimientos.vista.VistaMantenimiento;
import org.example.scdeedec.componentes.usuarios.controller.UsuarioController;
import org.example.scdeedec.componentes.mantenimientos.controller.MantenimientoController;
import org.example.scdeedec.componentes.usuarios.vista.VistaUsuario;

import javax.swing.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    public VistaPrincipal() {
        setTitle("🖥️ Sistema de Control de Equipos");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Solo acá cierra toda la app
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblTitulo = new JLabel("Sistema de Control de Equipos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnEquipo = new JButton("🖥️ Gestión de Equipos");
        btnEquipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEquipo.setMaximumSize(new Dimension(300, 40));
        btnEquipo.setBackground(new Color(0, 123, 255));
        btnEquipo.setForeground(Color.BLACK);
        btnEquipo.addActionListener(e -> abrirEquipos());

        JButton btnUsuarios = new JButton("👤 Gestión de Usuarios");
        btnUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUsuarios.setMaximumSize(new Dimension(300, 40));
        btnUsuarios.setBackground(new Color(0, 123, 255));
        btnUsuarios.setForeground(Color.BLACK);
        btnUsuarios.addActionListener(e -> abrirUsuarios());

        JButton btnMantenimientos = new JButton("🔧 Gestión de Mantenimientos");
        btnMantenimientos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMantenimientos.setMaximumSize(new Dimension(300, 40));
        btnMantenimientos.setBackground(new Color(40, 167, 69));
        btnMantenimientos.setForeground(Color.BLACK);
        btnMantenimientos.addActionListener(e -> abrirMantenimientos());

        JButton btnSalir = new JButton("🚪 Salir");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(300, 40));
        btnSalir.setBackground(new Color(108, 117, 125));
        btnSalir.setForeground(Color.BLACK);
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(btnEquipo);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(btnUsuarios);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(btnMantenimientos);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(btnSalir);

        add(panel);
        setVisible(true);
    }

    private void abrirEquipos() {
        VistaEquipo vista = new VistaEquipo();
        new EquipoController(vista);
    }
    private void abrirUsuarios() {
        VistaUsuario vista = new VistaUsuario();
        new UsuarioController(vista);
    }

    private void abrirMantenimientos() {
        VistaMantenimiento vista = new VistaMantenimiento();
        new MantenimientoController(vista);
    }
}
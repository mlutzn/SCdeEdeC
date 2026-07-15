package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaMantenimiento extends JFrame {
    private JTextField txtIdEquipo, txtDescripcion, txtTecnico, txtIdBuscar;
    private JComboBox<String> cmbTipo;
    private JTextArea txtObservaciones;
    private JButton btnRegistrar, btnConsultar, btnLimpiar, btnEliminar, btnBuscarEquipo;
    private JTable tablaMantenimientos;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    public VistaMantenimiento() {
        setTitle("📋 Sistema de Control de Equipos - Mantenimientos");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("🔧 Registrar Nuevo Mantenimiento"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("ID Equipo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtIdEquipo = new JTextField(10);
        panelFormulario.add(txtIdEquipo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtDescripcion = new JTextField(30);
        panelFormulario.add(txtDescripcion, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        cmbTipo = new JComboBox<>(new String[]{"preventivo", "correctivo"});
        panelFormulario.add(cmbTipo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Técnico:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtTecnico = new JTextField(20);
        panelFormulario.add(txtTecnico, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Observaciones:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtObservaciones = new JTextArea(3, 30);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        panelFormulario.add(scrollObs, gbc);

        // Botones
        row++;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnRegistrar = new JButton("💾 Registrar");
        btnRegistrar.setBackground(new Color(40, 167, 69));
        btnRegistrar.setForeground(Color.WHITE);

        btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setBackground(new Color(108, 117, 125));
        btnLimpiar.setForeground(Color.WHITE);

        btnBuscarEquipo = new JButton("🔍 Buscar por Equipo");
        btnBuscarEquipo.setBackground(new Color(0, 123, 255));
        btnBuscarEquipo.setForeground(Color.WHITE);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnBuscarEquipo);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panelFormulario.add(panelBotones, gbc);

        // Panel búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("🔎 Búsqueda Rápida"));
        panelBusqueda.add(new JLabel("Buscar por ID:"));
        txtIdBuscar = new JTextField(10);
        panelBusqueda.add(txtIdBuscar);
        btnConsultar = new JButton("📋 Ver Todos");
        btnConsultar.setBackground(new Color(23, 162, 184));
        btnConsultar.setForeground(Color.WHITE);
        panelBusqueda.add(btnConsultar);
        btnEliminar = new JButton("🗑️ Eliminar Seleccionado");
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        panelBusqueda.add(btnEliminar);

        // Panel stats
        JPanel panelStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelStats.setBorder(BorderFactory.createTitledBorder("📊 Estadísticas"));
        lblTotal = new JLabel("Total: 0 mantenimientos");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(0, 120, 215));
        panelStats.add(lblTotal);

        // Panel superior combinado
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBusqueda, BorderLayout.SOUTH);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Equipo", "Descripción", "Fecha", "Tipo", "Técnico", "Observaciones"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMantenimientos = new JTable(modeloTabla);
        tablaMantenimientos.setRowHeight(25);
        tablaMantenimientos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaMantenimientos.getTableHeader().setBackground(new Color(52, 58, 64));
        tablaMantenimientos.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaMantenimientos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("📋 Historial de Mantenimientos"));

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelStats, BorderLayout.NORTH);
        panelInferior.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelInferior, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }

    // Getters
    public JTextField getTxtIdEquipo() { return txtIdEquipo; }
    public JTextField getTxtDescripcion() { return txtDescripcion; }
    public JTextField getTxtTecnico() { return txtTecnico; }
    public JTextField getTxtIdBuscar() { return txtIdBuscar; }
    public JComboBox<String> getCmbTipo() { return cmbTipo; }
    public JTextArea getTxtObservaciones() { return txtObservaciones; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnConsultar() { return btnConsultar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnBuscarEquipo() { return btnBuscarEquipo; }
    public JTable getTablaMantenimientos() { return tablaMantenimientos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JLabel getLblTotal() { return lblTotal; }

    public void limpiarCampos() {
        txtIdEquipo.setText("");
        txtDescripcion.setText("");
        txtTecnico.setText("");
        txtObservaciones.setText("");
        cmbTipo.setSelectedIndex(0);
        txtIdBuscar.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "❌ Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "✅ Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
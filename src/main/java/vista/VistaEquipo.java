package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static util.IconUtil.cargarIcono;

public class VistaEquipo extends JFrame {
    private JTextField txtTipo, txtMarca, txtModelo, txtNumeroSerie, txtFechaAdquisicion, txtUbicacion;
    private JComboBox<String> cmbEstado;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar, btnConsultar;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    public VistaEquipo() {
        setTitle("🖥️ Sistema de Control de Equipos - Equipos");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("🖥️ Registrar / Editar Equipo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtTipo = new JTextField(20);
        panelFormulario.add(txtTipo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtMarca = new JTextField(20);
        panelFormulario.add(txtMarca, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtModelo = new JTextField(20);
        panelFormulario.add(txtModelo, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("N° Serie:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtNumeroSerie = new JTextField(20);
        panelFormulario.add(txtNumeroSerie, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Fecha Adquisición (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtFechaAdquisicion = new JTextField(20);
        panelFormulario.add(txtFechaAdquisicion, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        cmbEstado = new JComboBox<>(new String[]{"Activo", "En mantenimiento", "Fuera de servicio", "Baja"});
        panelFormulario.add(cmbEstado, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        txtUbicacion = new JTextField(20);
        panelFormulario.add(txtUbicacion, gbc);

        row++;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnRegistrar = new JButton("💾 Registrar");
        btnRegistrar.setBackground(new Color(40, 167, 69));
        btnRegistrar.setForeground(Color.BLACK);

        btnActualizar = new JButton("✏️ Actualizar");
        btnActualizar.setBackground(new Color(0, 123, 255));
        btnActualizar.setForeground(Color.BLACK);

        btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.BLACK);


        btnLimpiar = new JButton("Limpiar", cargarIcono("/icons/broom.png", 13, 13));
        btnLimpiar.setBackground(new Color(108, 117, 125));
        btnLimpiar.setForeground(Color.BLACK);

        btnConsultar = new JButton("📋 Ver Todos");
        btnConsultar.setBackground(new Color(23, 162, 184));
        btnConsultar.setForeground(Color.BLACK);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnConsultar);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1.0;
        panelFormulario.add(panelBotones, gbc);

        // Panel stats
        JPanel panelStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelStats.setBorder(BorderFactory.createTitledBorder("📊 Estadísticas"));
        lblTotal = new JLabel("Total: 0 equipos");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(0, 120, 215));
        panelStats.add(lblTotal);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Marca", "Modelo", "N° Serie", "Fecha Adquisición", "Estado", "Ubicación"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.setRowHeight(25);
        tablaEquipos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaEquipos.getTableHeader().setBackground(new Color(52, 58, 64));
        tablaEquipos.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scrollTabla = new JScrollPane(tablaEquipos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("🖥️ Equipos Registrados"));

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelStats, BorderLayout.NORTH);
        panelInferior.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(panelInferior, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setBackground(fondo);
        btn.setForeground(Color.BLACK);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    // Getters
    public JTextField getTxtTipo() { return txtTipo; }
    public JTextField getTxtMarca() { return txtMarca; }
    public JTextField getTxtModelo() { return txtModelo; }
    public JTextField getTxtNumeroSerie() { return txtNumeroSerie; }
    public JTextField getTxtFechaAdquisicion() { return txtFechaAdquisicion; }
    public JComboBox<String> getCmbEstado() { return cmbEstado; }
    public JTextField getTxtUbicacion() { return txtUbicacion; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnConsultar() { return btnConsultar; }
    public JTable getTablaEquipos() { return tablaEquipos; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JLabel getLblTotal() { return lblTotal; }

    public void limpiarCampos() {
        txtTipo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtNumeroSerie.setText("");
        txtFechaAdquisicion.setText("");
        txtUbicacion.setText("");
        cmbEstado.setSelectedIndex(0);
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
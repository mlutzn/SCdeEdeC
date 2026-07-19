package vista;


//Atributos y arranque del constructor


import modelo.EquipoItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static util.IconUtil.cargarIcono;

public class VistaUsuario extends JFrame {
    private JTextField txtNombre, txtApellido, txtEmail, txtTelefono;
    private JComboBox<EquipoItem> cmbEquipo;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar, btnConsultar;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    public VistaUsuario() {
        setTitle("👤 Sistema de Control de Equipos - Usuarios");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Parte 2 — El formulario

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("📝 Registrar / Editar Usuario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtApellido = new JTextField(20);
        panelFormulario.add(txtApellido, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtEmail = new JTextField(20);
        panelFormulario.add(txtEmail, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        txtTelefono = new JTextField(20);
        panelFormulario.add(txtTelefono, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.2;
        panelFormulario.add(new JLabel("Equipo asignado:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.8;
        cmbEquipo = new JComboBox<>();
        panelFormulario.add(cmbEquipo, gbc);

        //la Vista no debe saber nada de SQL — solo el Controller y el DAO/Service.
        // Parte 3 Botones, tabla y cierre del constructor

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

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panelFormulario.add(panelBotones, gbc);

        // Panel de estadísticas
        JPanel panelStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelStats.setBorder(BorderFactory.createTitledBorder("📊 Estadísticas"));
        lblTotal = new JLabel("Total: 0 usuarios");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(0, 120, 215));
        panelStats.add(lblTotal);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido", "Email", "Teléfono", "ID Equipo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setRowHeight(25);
        tablaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaUsuarios.getTableHeader().setBackground(new Color(52, 58, 64));
        tablaUsuarios.getTableHeader().setForeground(Color.BLACK);

        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("📋 Usuarios Registrados"));

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelStats, BorderLayout.NORTH);
        panelInferior.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(panelInferior, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }

//Parte 4 — Getters y métodos de utilidad de VistaUsuario
//Son los que el Controller necesita para poder leer los campos y reaccionar a los botones.


    // Getters de los campos de texto y combo
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JComboBox<EquipoItem> getCmbEquipo() {
        return cmbEquipo;
    }

    // Getters de los botones
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnConsultar() {
        return btnConsultar;
    }

    // Getters de la tabla
    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    // Limpia el formulario después de registrar/actualizar
    public void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        cmbEquipo.setSelectedIndex(-1);
    }

    // Popups para comunicarle al usuario qué pasó
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "❌ Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "✅ Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}


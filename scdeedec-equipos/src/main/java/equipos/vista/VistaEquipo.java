package equipos.vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class VistaEquipo extends JFrame {

    private JTextField txtTipo;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtNumeroSerie;
    private JTextField txtFechaAdquisicion;
    private JTextField txtUbicacion;
    private JTextField txtBuscar;

    private JComboBox<String> cmbEstado;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnConsultar;
    private JButton btnBuscar;

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    public VistaEquipo() {

        setTitle("Gestión de Equipos de Cómputo");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelPrincipal =
                new JPanel(new BorderLayout(10, 10));

        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        panelPrincipal.add(
                crearFormulario(),
                BorderLayout.NORTH
        );

        panelPrincipal.add(
                crearPanelTabla(),
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        setVisible(true);
    }

    private JPanel crearFormulario() {

        JPanel panelFormulario =
                new JPanel(new GridBagLayout());

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Registrar o editar equipo"
                )
        );

        GridBagConstraints posicion =
                new GridBagConstraints();

        posicion.insets = new Insets(5, 5, 5, 5);
        posicion.fill = GridBagConstraints.HORIZONTAL;

        txtTipo = new JTextField(20);
        txtMarca = new JTextField(20);
        txtModelo = new JTextField(20);
        txtNumeroSerie = new JTextField(20);
        txtFechaAdquisicion = new JTextField(20);
        txtUbicacion = new JTextField(20);

        cmbEstado = new JComboBox<>(new String[]{
                "Activo",
                "En mantenimiento",
                "Fuera de servicio",
                "Baja"
        });

        agregarCampo(
                panelFormulario,
                posicion,
                0,
                "Tipo:",
                txtTipo
        );

        agregarCampo(
                panelFormulario,
                posicion,
                1,
                "Marca:",
                txtMarca
        );

        agregarCampo(
                panelFormulario,
                posicion,
                2,
                "Modelo:",
                txtModelo
        );

        agregarCampo(
                panelFormulario,
                posicion,
                3,
                "Número de serie:",
                txtNumeroSerie
        );

        agregarCampo(
                panelFormulario,
                posicion,
                4,
                "Fecha de adquisición (dd/MM/yyyy):",
                txtFechaAdquisicion
        );

        posicion.gridx = 0;
        posicion.gridy = 5;
        posicion.weightx = 0.2;

        panelFormulario.add(
                new JLabel("Estado:"),
                posicion
        );

        posicion.gridx = 1;
        posicion.weightx = 0.8;

        panelFormulario.add(
                cmbEstado,
                posicion
        );

        agregarCampo(
                panelFormulario,
                posicion,
                6,
                "Ubicación:",
                txtUbicacion
        );

        JPanel panelBotones =
                crearPanelBotones();

        posicion.gridx = 0;
        posicion.gridy = 7;
        posicion.gridwidth = 2;
        posicion.weightx = 1;

        panelFormulario.add(
                panelBotones,
                posicion
        );

        return panelFormulario;
    }

    private void agregarCampo(
            JPanel panel,
            GridBagConstraints posicion,
            int fila,
            String texto,
            JTextField campo
    ) {
        posicion.gridwidth = 1;
        posicion.gridx = 0;
        posicion.gridy = fila;
        posicion.weightx = 0.2;

        panel.add(
                new JLabel(texto),
                posicion
        );

        posicion.gridx = 1;
        posicion.weightx = 0.8;

        panel.add(
                campo,
                posicion
        );
    }

    private JPanel crearPanelBotones() {

        JPanel panelBotones =
                new JPanel(new FlowLayout());

        btnRegistrar =
                new JButton("Registrar");

        btnActualizar =
                new JButton("Actualizar");

        btnEliminar =
                new JButton("Eliminar");

        btnLimpiar =
                new JButton("Limpiar");

        btnConsultar =
                new JButton("Ver todos");

        btnRegistrar.setBackground(
                new Color(40, 167, 69)
        );

        btnActualizar.setBackground(
                new Color(0, 123, 255)
        );

        btnEliminar.setBackground(
                new Color(220, 53, 69)
        );

        btnLimpiar.setBackground(
                new Color(108, 117, 125)
        );

        btnConsultar.setBackground(
                new Color(23, 162, 184)
        );

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnConsultar);

        return panelBotones;
    }

    private JPanel crearPanelTabla() {

        JPanel panelInferior =
                new JPanel(new BorderLayout(5, 5));

        JPanel panelBusqueda =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelBusqueda.setBorder(
                BorderFactory.createTitledBorder(
                        "Consultar equipos"
                )
        );

        lblTotal = new JLabel("Total: 0 equipos");
        lblTotal.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        txtBuscar = new JTextField(25);
        txtBuscar.setToolTipText(
                "Marca, modelo, serie, estado o ubicación"
        );

        btnBuscar = new JButton("Buscar");

        panelBusqueda.add(lblTotal);
        panelBusqueda.add(
                Box.createHorizontalStrut(20)
        );
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);

        modeloTabla = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Tipo",
                        "Marca",
                        "Modelo",
                        "Número de serie",
                        "Fecha de adquisición",
                        "Estado",
                        "Ubicación"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int fila,
                    int columna
            ) {
                return false;
            }
        };

        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.setRowHeight(25);
        tablaEquipos.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION
        );

        tablaEquipos
                .getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        JScrollPane desplazamiento =
                new JScrollPane(tablaEquipos);

        desplazamiento.setBorder(
                BorderFactory.createTitledBorder(
                        "Equipos registrados"
                )
        );

        panelInferior.add(
                panelBusqueda,
                BorderLayout.NORTH
        );

        panelInferior.add(
                desplazamiento,
                BorderLayout.CENTER
        );

        return panelInferior;
    }

    public void limpiarCampos() {

        txtTipo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtNumeroSerie.setText("");
        txtFechaAdquisicion.setText("");
        txtUbicacion.setText("");

        cmbEstado.setSelectedIndex(0);
        tablaEquipos.clearSelection();

        txtTipo.requestFocus();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Operación exitosa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public JTextField getTxtTipo() {
        return txtTipo;
    }

    public JTextField getTxtMarca() {
        return txtMarca;
    }

    public JTextField getTxtModelo() {
        return txtModelo;
    }

    public JTextField getTxtNumeroSerie() {
        return txtNumeroSerie;
    }

    public JTextField getTxtFechaAdquisicion() {
        return txtFechaAdquisicion;
    }

    public JTextField getTxtUbicacion() {
        return txtUbicacion;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JComboBox<String> getCmbEstado() {
        return cmbEstado;
    }

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

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTablaEquipos() {
        return tablaEquipos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }
}
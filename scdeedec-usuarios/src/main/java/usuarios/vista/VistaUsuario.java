package usuarios.vista;

import equipos.modelo.EquipoItem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaUsuario extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;

    private JComboBox<EquipoItem> cmbEquipo;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnConsultar;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private static final Color COLOR_FONDO =
            new Color(244, 247, 251);

    private static final Color COLOR_TARJETA =
            Color.WHITE;

    private static final Color COLOR_TEXTO =
            new Color(30, 41, 59);

    private static final Color COLOR_SECUNDARIO =
            new Color(100, 116, 139);

    private static final Color COLOR_BORDE =
            new Color(226, 232, 240);

    private static final Color COLOR_AZUL =
            new Color(37, 99, 235);

    private static final Color COLOR_VERDE =
            new Color(22, 163, 74);

    private static final Color COLOR_ROJO =
            new Color(220, 38, 38);

    public VistaUsuario() {

        setTitle("Gestión de usuarios");
        setSize(1120, 720);
        setMinimumSize(new Dimension(950, 620));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(
                new BorderLayout(0, 18)
        );

        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(
                        24, 28, 24, 28
                )
        );

        panelPrincipal.add(
                crearEncabezado(),
                BorderLayout.NORTH
        );

        JPanel contenido = new JPanel(
                new BorderLayout(0, 18)
        );

        contenido.setOpaque(false);

        contenido.add(
                crearFormulario(),
                BorderLayout.NORTH
        );

        contenido.add(
                crearPanelTabla(),
                BorderLayout.CENTER
        );

        panelPrincipal.add(
                contenido,
                BorderLayout.CENTER
        );

        setContentPane(panelPrincipal);

        getRootPane().setDefaultButton(btnRegistrar);

        setVisible(true);
    }

    private JPanel crearEncabezado() {

        JPanel encabezado = new JPanel();
        encabezado.setLayout(
                new BoxLayout(encabezado, BoxLayout.Y_AXIS)
        );

        encabezado.setOpaque(false);

        JLabel titulo = new JLabel(
                "Gestión de usuarios"
        );

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 28)
        );

        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Registrá, editá y asigná equipos a los usuarios del sistema"
        );

        subtitulo.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        subtitulo.setForeground(COLOR_SECUNDARIO);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(4));
        encabezado.add(subtitulo);

        return encabezado;
    }

    private JPanel crearFormulario() {

        JPanel tarjeta = crearTarjeta(
                new BorderLayout(0, 18)
        );

        JPanel encabezadoFormulario = new JPanel();
        encabezadoFormulario.setLayout(
                new BoxLayout(
                        encabezadoFormulario,
                        BoxLayout.Y_AXIS
                )
        );

        encabezadoFormulario.setOpaque(false);

        JLabel titulo = new JLabel(
                "Información del usuario"
        );

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 18)
        );

        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descripcion = new JLabel(
                "Completá los datos y seleccioná el equipo correspondiente"
        );

        descripcion.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        descripcion.setForeground(COLOR_SECUNDARIO);
        descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        encabezadoFormulario.add(titulo);
        encabezadoFormulario.add(Box.createVerticalStrut(3));
        encabezadoFormulario.add(descripcion);

        tarjeta.add(
                encabezadoFormulario,
                BorderLayout.NORTH
        );

        JPanel campos = new JPanel(
                new GridLayout(3, 2, 16, 14)
        );

        campos.setOpaque(false);

        txtNombre = crearCampoTexto(
                "Ej. Natanael"
        );

        txtApellido = crearCampoTexto(
                "Ej. Solís"
        );

        txtEmail = crearCampoTexto(
                "usuario@correo.com"
        );

        txtTelefono = crearCampoTexto(
                "8888-8888"
        );

        cmbEquipo = new JComboBox<>();
        estilizarEntrada(cmbEquipo);

        campos.add(
                crearGrupoCampo(
                        "Nombre",
                        txtNombre
                )
        );

        campos.add(
                crearGrupoCampo(
                        "Apellido",
                        txtApellido
                )
        );

        campos.add(
                crearGrupoCampo(
                        "Correo electrónico",
                        txtEmail
                )
        );

        campos.add(
                crearGrupoCampo(
                        "Teléfono",
                        txtTelefono
                )
        );

        campos.add(
                crearGrupoCampo(
                        "Equipo asignado",
                        cmbEquipo
                )
        );

        JPanel espacioVacio = new JPanel();
        espacioVacio.setOpaque(false);
        campos.add(espacioVacio);

        tarjeta.add(
                campos,
                BorderLayout.CENTER
        );

        JPanel panelBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        0
                )
        );

        panelBotones.setOpaque(false);

        btnLimpiar = crearBoton(
                "Limpiar",
                new Color(100, 116, 139)
        );

        btnConsultar = crearBoton(
                "Ver todos",
                new Color(8, 145, 178)
        );

        btnEliminar = crearBoton(
                "Eliminar",
                COLOR_ROJO
        );

        btnActualizar = crearBoton(
                "Actualizar",
                COLOR_AZUL
        );

        btnRegistrar = crearBoton(
                "Registrar",
                COLOR_VERDE
        );

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnRegistrar);

        tarjeta.add(
                panelBotones,
                BorderLayout.SOUTH
        );

        return tarjeta;
    }

    private JPanel crearPanelTabla() {

        JPanel tarjeta = crearTarjeta(
                new BorderLayout(0, 14)
        );

        JPanel encabezadoTabla = new JPanel(
                new BorderLayout()
        );

        encabezadoTabla.setOpaque(false);

        JLabel titulo = new JLabel(
                "Usuarios registrados"
        );

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 18)
        );

        titulo.setForeground(COLOR_TEXTO);

        lblTotal = new JLabel(
                "Total: 0 usuarios"
        );

        lblTotal.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        lblTotal.setForeground(COLOR_AZUL);
        lblTotal.setOpaque(true);

        lblTotal.setBackground(
                new Color(239, 246, 255)
        );

        lblTotal.setBorder(
                BorderFactory.createEmptyBorder(
                        7, 12, 7, 12
                )
        );

        encabezadoTabla.add(
                titulo,
                BorderLayout.WEST
        );

        encabezadoTabla.add(
                lblTotal,
                BorderLayout.EAST
        );

        tarjeta.add(
                encabezadoTabla,
                BorderLayout.NORTH
        );

        modeloTabla = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Nombre",
                        "Apellido",
                        "Correo electrónico",
                        "Teléfono",
                        "ID Equipo"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);

        tablaUsuarios.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        tablaUsuarios.setRowHeight(36);
        tablaUsuarios.setFillsViewportHeight(true);
        tablaUsuarios.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaUsuarios.setShowVerticalLines(false);
        tablaUsuarios.setShowHorizontalLines(true);

        tablaUsuarios.setGridColor(
                new Color(241, 245, 249)
        );

        tablaUsuarios.setIntercellSpacing(
                new Dimension(0, 1)
        );

        tablaUsuarios.setAutoCreateRowSorter(true);

        tablaUsuarios.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        tablaUsuarios.getTableHeader().setBackground(
                new Color(15, 23, 42)
        );

        tablaUsuarios.getTableHeader().setForeground(
                Color.WHITE
        );

        tablaUsuarios.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        tablaUsuarios.getTableHeader().setReorderingAllowed(
                false
        );

        tablaUsuarios.setDefaultRenderer(
                Object.class,
                crearRenderizadorTabla()
        );

        configurarColumnas();

        JScrollPane scrollTabla =
                new JScrollPane(tablaUsuarios);

        scrollTabla.setBorder(
                BorderFactory.createLineBorder(
                        COLOR_BORDE
                )
        );

        scrollTabla.getViewport().setBackground(
                Color.WHITE
        );

        tarjeta.add(
                scrollTabla,
                BorderLayout.CENTER
        );

        return tarjeta;
    }

    private JTextField crearCampoTexto(
            String placeholder
    ) {

        JTextField campo = new JTextField();

        campo.putClientProperty(
                "JTextField.placeholderText",
                placeholder
        );

        estilizarEntrada(campo);

        return campo;
    }

    private JPanel crearGrupoCampo(
            String texto,
            JComponent componente
    ) {

        JPanel grupo = new JPanel();

        grupo.setLayout(
                new BoxLayout(grupo, BoxLayout.Y_AXIS)
        );

        grupo.setOpaque(false);

        JLabel etiqueta = new JLabel(texto);

        etiqueta.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        etiqueta.setForeground(COLOR_TEXTO);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        componente.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        grupo.add(etiqueta);
        grupo.add(Box.createVerticalStrut(6));
        grupo.add(componente);

        return grupo;
    }

    private void estilizarEntrada(
            JComponent componente
    ) {

        componente.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        componente.setPreferredSize(
                new Dimension(100, 38)
        );

        componente.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        componente.putClientProperty(
                "JComponent.roundRect",
                true
        );
    }

    private JButton crearBoton(
            String texto,
            Color color
    ) {

        JButton boton = new JButton(texto);

        boton.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);

        boton.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        boton.setPreferredSize(
                new Dimension(112, 38)
        );

        boton.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );

        return boton;
    }

    private JPanel crearTarjeta(
            LayoutManager layout
    ) {

        JPanel tarjeta = new JPanel(layout);

        tarjeta.setBackground(COLOR_TARJETA);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                COLOR_BORDE
                        ),
                        BorderFactory.createEmptyBorder(
                                18, 20, 18, 20
                        )
                )
        );

        return tarjeta;
    }

    private DefaultTableCellRenderer
    crearRenderizadorTabla() {

        return new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
            ) {

                Component componente =
                        super.getTableCellRendererComponent(
                                table,
                                value,
                                isSelected,
                                hasFocus,
                                row,
                                column
                        );

                if (isSelected) {
                    componente.setBackground(
                            new Color(219, 234, 254)
                    );

                    componente.setForeground(
                            new Color(15, 23, 42)
                    );
                } else {
                    componente.setBackground(
                            row % 2 == 0
                                    ? Color.WHITE
                                    : new Color(
                                    248,
                                    250,
                                    252
                            )
                    );

                    componente.setForeground(
                            COLOR_TEXTO
                    );
                }

                if (componente instanceof JComponent) {
                    ((JComponent) componente).setBorder(
                            BorderFactory.createEmptyBorder(
                                    0, 10, 0, 10
                            )
                    );
                }

                return componente;
            }
        };
    }

    private void configurarColumnas() {

        tablaUsuarios
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(45);

        tablaUsuarios
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(120);

        tablaUsuarios
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(120);

        tablaUsuarios
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(230);

        tablaUsuarios
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(110);

        tablaUsuarios
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(75);
    }

    // Getters que utiliza UsuarioController

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

    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        cmbEquipo.setSelectedIndex(-1);
        tablaUsuarios.clearSelection();
        txtNombre.requestFocus();
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
                "Operación completada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
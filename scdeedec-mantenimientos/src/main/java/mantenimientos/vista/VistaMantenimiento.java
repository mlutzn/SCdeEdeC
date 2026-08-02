package mantenimientos.vista;

import equipos.modelo.EquipoItem;
import usuarios.modelo.UsuarioItem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaMantenimiento extends JFrame {

    private JTextField txtDescripcion;
    private JTextField txtIdBuscar;

    private JComboBox<EquipoItem> cmbEquipo;
    private JComboBox<UsuarioItem> cmbTecnico;
    private JComboBox<String> cmbTipo;

    private JTextArea txtObservaciones;

    private JButton btnRegistrar;
    private JButton btnConsultar;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    private JButton btnBuscarEquipo;

    private JTable tablaMantenimientos;
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

    private static final Color COLOR_NARANJA =
            new Color(234, 88, 12);

    public VistaMantenimiento() {

        setTitle("Gestión de mantenimientos");
        setSize(1180, 960);
        setMinimumSize(new Dimension(1000, 650));
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
                "Gestión de mantenimientos"
        );

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 28)
        );

        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel(
                "Registrá mantenimientos y consultá el historial de cada equipo"
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
                "Nuevo mantenimiento"
        );

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 18)
        );

        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descripcion = new JLabel(
                "Seleccioná el equipo, el técnico y completá la información del trabajo"
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
                new GridBagLayout()
        );

        campos.setOpaque(false);

        cmbEquipo = new JComboBox<>();
        estilizarEntrada(cmbEquipo);

        cmbTecnico = new JComboBox<>();
        estilizarEntrada(cmbTecnico);

        txtDescripcion = crearCampoTexto(
                "Describí el mantenimiento realizado"
        );

        cmbTipo = new JComboBox<>(
                new String[]{
                        "preventivo",
                        "correctivo"
                }
        );

        estilizarEntrada(cmbTipo);

        txtObservaciones = new JTextArea(3, 30);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        txtObservaciones.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        txtObservaciones.putClientProperty(
                "JTextArea.placeholderText",
                "Agregá observaciones adicionales"
        );

        JScrollPane scrollObservaciones =
                new JScrollPane(txtObservaciones);

        scrollObservaciones.setPreferredSize(
                new Dimension(100, 88)
        );

        scrollObservaciones.setBorder(
                BorderFactory.createLineBorder(
                        COLOR_BORDE
                )
        );

        agregarCampo(
                campos,
                "Equipo",
                cmbEquipo,
                0,
                0,
                1
        );

        agregarCampo(
                campos,
                "Técnico responsable",
                cmbTecnico,
                1,
                0,
                1
        );

        agregarCampo(
                campos,
                "Descripción",
                txtDescripcion,
                0,
                1,
                1
        );

        agregarCampo(
                campos,
                "Tipo de mantenimiento",
                cmbTipo,
                1,
                1,
                1
        );

        agregarCampo(
                campos,
                "Observaciones",
                scrollObservaciones,
                0,
                2,
                2
        );

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
                new Color(100, 116, 139),
                110
        );

        btnBuscarEquipo = crearBoton(
                "Buscar por equipo",
                COLOR_AZUL,
                155
        );

        btnRegistrar = crearBoton(
                "Registrar",
                COLOR_VERDE,
                120
        );

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnBuscarEquipo);
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

        JPanel parteSuperior = new JPanel(
                new BorderLayout(0, 12)
        );

        parteSuperior.setOpaque(false);

        JPanel encabezadoTabla = new JPanel(
                new BorderLayout()
        );

        encabezadoTabla.setOpaque(false);

        JLabel titulo = new JLabel(
                "Historial de mantenimientos"
        );

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 18)
        );

        titulo.setForeground(COLOR_TEXTO);

        lblTotal = new JLabel(
                "Total: 0 mantenimientos"
        );

        lblTotal.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        lblTotal.setForeground(COLOR_NARANJA);
        lblTotal.setOpaque(true);

        lblTotal.setBackground(
                new Color(255, 247, 237)
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

        JPanel panelBusqueda = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                )
        );

        panelBusqueda.setOpaque(false);

        JLabel etiquetaBuscar =
                new JLabel("Buscar por ID:");

        etiquetaBuscar.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        etiquetaBuscar.setForeground(COLOR_TEXTO);

        txtIdBuscar = crearCampoTexto(
                "Ej. 1"
        );

        txtIdBuscar.setPreferredSize(
                new Dimension(130, 38)
        );

        btnConsultar = crearBoton(
                "Ver todos",
                new Color(8, 145, 178),
                115
        );

        btnEliminar = crearBoton(
                "Eliminar seleccionado",
                COLOR_ROJO,
                175
        );

        panelBusqueda.add(etiquetaBuscar);
        panelBusqueda.add(txtIdBuscar);
        panelBusqueda.add(btnConsultar);
        panelBusqueda.add(btnEliminar);

        parteSuperior.add(
                encabezadoTabla,
                BorderLayout.NORTH
        );

        parteSuperior.add(
                panelBusqueda,
                BorderLayout.SOUTH
        );

        tarjeta.add(
                parteSuperior,
                BorderLayout.NORTH
        );

        modeloTabla = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Equipo",
                        "Descripción",
                        "Fecha",
                        "Tipo",
                        "Técnico",
                        "Observaciones"
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

        tablaMantenimientos =
                new JTable(modeloTabla);

        tablaMantenimientos.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        tablaMantenimientos.setRowHeight(36);

        tablaMantenimientos.setFillsViewportHeight(
                true
        );

        tablaMantenimientos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaMantenimientos.setShowVerticalLines(
                false
        );

        tablaMantenimientos.setShowHorizontalLines(
                true
        );

        tablaMantenimientos.setGridColor(
                new Color(241, 245, 249)
        );

        tablaMantenimientos.setIntercellSpacing(
                new Dimension(0, 1)
        );

        tablaMantenimientos.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        tablaMantenimientos.getTableHeader().setBackground(
                new Color(15, 23, 42)
        );

        tablaMantenimientos.getTableHeader().setForeground(
                Color.WHITE
        );

        tablaMantenimientos.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        tablaMantenimientos
                .getTableHeader()
                .setReorderingAllowed(false);

        tablaMantenimientos.setDefaultRenderer(
                Object.class,
                crearRenderizadorTabla()
        );

        configurarColumnas();

        JScrollPane scrollTabla =
                new JScrollPane(tablaMantenimientos);

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

    private void agregarCampo(
            JPanel panel,
            String texto,
            JComponent componente,
            int columna,
            int fila,
            int ancho
    ) {

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;

        gbc.weightx = ancho == 2
                ? 1.0
                : 0.5;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.insets = new Insets(
                6,
                columna == 0 ? 0 : 8,
                6,
                columna + ancho >= 2 ? 0 : 8
        );

        panel.add(
                crearGrupoCampo(
                        texto,
                        componente
                ),
                gbc
        );
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
            Color color,
            int ancho
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
                new Dimension(ancho, 38)
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
                            new Color(255, 237, 213)
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

        tablaMantenimientos
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(45);

        tablaMantenimientos
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(170);

        tablaMantenimientos
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(230);

        tablaMantenimientos
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(90);

        tablaMantenimientos
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(100);

        tablaMantenimientos
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(150);

        tablaMantenimientos
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(230);
    }

    // Getters utilizados por MantenimientoController

    public JComboBox<EquipoItem> getCmbEquipo() {
        return cmbEquipo;
    }

    public JComboBox<UsuarioItem> getCmbTecnico() {
        return cmbTecnico;
    }

    public JTextField getTxtDescripcion() {
        return txtDescripcion;
    }

    public JTextField getTxtIdBuscar() {
        return txtIdBuscar;
    }

    public JComboBox<String> getCmbTipo() {
        return cmbTipo;
    }

    public JTextArea getTxtObservaciones() {
        return txtObservaciones;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnConsultar() {
        return btnConsultar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnBuscarEquipo() {
        return btnBuscarEquipo;
    }

    public JTable getTablaMantenimientos() {
        return tablaMantenimientos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void limpiarCampos() {

        cmbEquipo.setSelectedIndex(-1);
        cmbTecnico.setSelectedIndex(-1);

        txtDescripcion.setText("");
        txtObservaciones.setText("");

        cmbTipo.setSelectedIndex(0);
        txtIdBuscar.setText("");

        tablaMantenimientos.clearSelection();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
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
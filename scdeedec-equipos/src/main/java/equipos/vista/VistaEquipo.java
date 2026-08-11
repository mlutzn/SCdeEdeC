package equipos.vista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import util.DatePickerField;

public class VistaEquipo extends JFrame {

    private static final Color FONDO =
            new Color(244, 247, 251);

    private static final Color TARJETA =
            Color.WHITE;

    private static final Color TEXTO =
            new Color(30, 41, 59);

    private static final Color TEXTO_SECUNDARIO =
            new Color(100, 116, 139);

    private static final Color BORDE =
            new Color(226, 232, 240);

    private static final Color PRIMARIO =
            new Color(37, 99, 235);

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setMinimumSize(
                new Dimension(1050, 680)
        );

        setSize(1200, 760);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel principal = new JPanel(
                new BorderLayout(0, 20)
        );

        principal.setBackground(FONDO);

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        24, 28, 28, 28
                )
        );

        principal.add(
                crearEncabezado(),
                BorderLayout.NORTH
        );

        JPanel contenido = new JPanel(
                new BorderLayout(0, 20)
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

        principal.add(contenido, BorderLayout.CENTER);

        setContentPane(principal);
        setVisible(true);
    }

    private JPanel crearEncabezado() {

        JPanel panel = new JPanel();
        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        JLabel titulo = new JLabel(
                "Gestión de equipos"
        );

        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 27)
        );

        titulo.setForeground(TEXTO);

        JLabel subtitulo = new JLabel(
                "Registre, consulte y actualice los equipos de cómputo"
        );

        subtitulo.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        subtitulo.setForeground(TEXTO_SECUNDARIO);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitulo);

        return panel;
    }

    private JPanel crearFormulario() {

        JPanel tarjeta = crearTarjeta(
                new BorderLayout(0, 16)
        );

        JLabel titulo = new JLabel(
                "Información del equipo"
        );

        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 18)
        );

        titulo.setForeground(TEXTO);

        JPanel campos = new JPanel(
                new GridBagLayout()
        );

        campos.setOpaque(false);

        GridBagConstraints posicion =
                new GridBagConstraints();

        txtTipo = crearCampo(
                "Ejemplo: Laptop, Desktop o Servidor"
        );

        txtMarca = crearCampo(
                "Marca del fabricante"
        );

        txtModelo = crearCampo(
                "Modelo del equipo"
        );

        txtNumeroSerie = crearCampo(
                "Número de serie único"
        );

        txtFechaAdquisicion = crearCampo(
                "dd/MM/yyyy"
        );

        // Botón de calendario reutilizado de scdeedec-common: escribe la
        // fecha elegida directamente en txtFechaAdquisicion, sin necesidad
        // de cambiar su tipo (sigue siendo un JTextField normal, tal como
        // lo espera EquipoController).
        JPanel panelFechaAdquisicion = new JPanel(new BorderLayout(4, 0));
        panelFechaAdquisicion.setOpaque(false);
        panelFechaAdquisicion.setPreferredSize(new Dimension(250, 36));
        panelFechaAdquisicion.add(txtFechaAdquisicion, BorderLayout.CENTER);
        panelFechaAdquisicion.add(
                DatePickerField.crearBotonCalendario(txtFechaAdquisicion, "dd/MM/yyyy"),
                BorderLayout.EAST
        );

        txtUbicacion = crearCampo(
                "Ejemplo: Oficina, Laboratorio o Bodega"
        );

        cmbEstado = new JComboBox<>(
                new String[]{
                        "Activo",
                        "En mantenimiento",
                        "Fuera de servicio",
                        "Baja"
                }
        );

        cmbEstado.setPreferredSize(
                new Dimension(250, 36)
        );

        agregarCampo(
                campos,
                posicion,
                0,
                0,
                "Tipo",
                txtTipo
        );

        agregarCampo(
                campos,
                posicion,
                2,
                0,
                "Marca",
                txtMarca
        );

        agregarCampo(
                campos,
                posicion,
                0,
                1,
                "Modelo",
                txtModelo
        );

        agregarCampo(
                campos,
                posicion,
                2,
                1,
                "Número de serie",
                txtNumeroSerie
        );

        agregarCampo(
                campos,
                posicion,
                0,
                2,
                "Fecha de adquisición",
                panelFechaAdquisicion
        );

        agregarCampo(
                campos,
                posicion,
                2,
                2,
                "Estado",
                cmbEstado
        );

        agregarCampoAncho(
                campos,
                posicion,
                3,
                "Ubicación",
                txtUbicacion
        );

        tarjeta.add(titulo, BorderLayout.NORTH);
        tarjeta.add(campos, BorderLayout.CENTER);

        tarjeta.add(
                crearPanelBotones(),
                BorderLayout.SOUTH
        );

        return tarjeta;
    }

    private JTextField crearCampo(
            String textoAyuda
    ) {

        JTextField campo = new JTextField();

        campo.setPreferredSize(
                new Dimension(250, 36)
        );

        campo.putClientProperty(
                "JTextField.placeholderText",
                textoAyuda
        );

        return campo;
    }

    private void agregarCampo(
            JPanel panel,
            GridBagConstraints posicion,
            int columna,
            int fila,
            String texto,
            JComponent componente
    ) {

        posicion.gridy = fila;
        posicion.gridwidth = 1;
        posicion.weighty = 0;
        posicion.anchor = GridBagConstraints.WEST;

        posicion.gridx = columna;
        posicion.weightx = 0;
        posicion.fill = GridBagConstraints.NONE;

        posicion.insets =
                columna == 0
                        ? new Insets(7, 0, 7, 10)
                        : new Insets(7, 28, 7, 10);

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );
        etiqueta.setForeground(TEXTO);

        panel.add(etiqueta, posicion);

        posicion.gridx = columna + 1;
        posicion.weightx = 1;
        posicion.fill =
                GridBagConstraints.HORIZONTAL;

        posicion.insets =
                new Insets(7, 0, 7, 0);

        panel.add(componente, posicion);
    }

    private void agregarCampoAncho(
            JPanel panel,
            GridBagConstraints posicion,
            int fila,
            String texto,
            JComponent componente
    ) {

        posicion.gridy = fila;
        posicion.gridx = 0;
        posicion.gridwidth = 1;
        posicion.weightx = 0;
        posicion.fill = GridBagConstraints.NONE;

        posicion.insets =
                new Insets(7, 0, 7, 10);

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );
        etiqueta.setForeground(TEXTO);

        panel.add(etiqueta, posicion);

        posicion.gridx = 1;
        posicion.gridwidth = 3;
        posicion.weightx = 1;
        posicion.fill =
                GridBagConstraints.HORIZONTAL;

        posicion.insets =
                new Insets(7, 0, 7, 0);

        panel.add(componente, posicion);

        posicion.gridwidth = 1;
    }

    private JPanel crearPanelBotones() {

        JPanel panel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                )
        );

        panel.setOpaque(false);

        btnRegistrar = crearBoton(
                "Registrar",
                new Color(22, 163, 74)
        );

        btnActualizar = crearBoton(
                "Actualizar",
                PRIMARIO
        );

        btnEliminar = crearBoton(
                "Eliminar",
                new Color(220, 38, 38)
        );

        btnLimpiar = crearBoton(
                "Limpiar",
                new Color(100, 116, 139)
        );

        btnConsultar = crearBoton(
                "Ver todos",
                new Color(8, 145, 178)
        );

        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnConsultar);

        return panel;
    }

    private JButton crearBoton(
            String texto,
            Color color
    ) {

        JButton boton = new JButton(texto);

        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        boton.setPreferredSize(
                new Dimension(115, 38)
        );

        boton.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        boton.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );

        return boton;
    }

    private JPanel crearPanelTabla() {

        JPanel tarjeta = crearTarjeta(
                new BorderLayout(0, 15)
        );

        JPanel cabecera = new JPanel(
                new BorderLayout()
        );

        cabecera.setOpaque(false);

        JPanel informacion = new JPanel();
        informacion.setOpaque(false);

        informacion.setLayout(
                new BoxLayout(
                        informacion,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titulo = new JLabel(
                "Equipos registrados"
        );

        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 18)
        );

        titulo.setForeground(TEXTO);

        lblTotal = new JLabel("Total: 0 equipos");

        lblTotal.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        lblTotal.setForeground(TEXTO_SECUNDARIO);

        informacion.add(titulo);
        informacion.add(Box.createVerticalStrut(3));
        informacion.add(lblTotal);

        JPanel busqueda = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        0
                )
        );

        busqueda.setOpaque(false);

        txtBuscar = crearCampo(
                "Marca, modelo, serie, estado o ubicación"
        );

        txtBuscar.setPreferredSize(
                new Dimension(310, 36)
        );

        btnBuscar = crearBoton(
                "Buscar",
                PRIMARIO
        );

        btnBuscar.setPreferredSize(
                new Dimension(95, 36)
        );

        busqueda.add(txtBuscar);
        busqueda.add(btnBuscar);

        cabecera.add(informacion, BorderLayout.WEST);
        cabecera.add(busqueda, BorderLayout.EAST);

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

        configurarTabla();

        JScrollPane desplazamiento =
                new JScrollPane(tablaEquipos);

        desplazamiento.setBorder(
                new LineBorder(BORDE, 1, true)
        );

        tarjeta.add(cabecera, BorderLayout.NORTH);
        tarjeta.add(
                desplazamiento,
                BorderLayout.CENTER
        );

        return tarjeta;
    }

    private void configurarTabla() {

        tablaEquipos.setRowHeight(34);
        tablaEquipos.setShowVerticalLines(false);
        tablaEquipos.setShowHorizontalLines(true);
        tablaEquipos.setGridColor(BORDE);
        tablaEquipos.setIntercellSpacing(
                new Dimension(0, 1)
        );

        tablaEquipos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tablaEquipos.setSelectionBackground(PRIMARIO);
        tablaEquipos.setSelectionForeground(Color.WHITE);
        tablaEquipos.setFillsViewportHeight(true);
        tablaEquipos.setAutoCreateRowSorter(true);

        tablaEquipos.getTableHeader().setReorderingAllowed(
                false
        );

        tablaEquipos.getTableHeader().setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        tablaEquipos.getTableHeader().setBackground(
                new Color(15, 23, 42)
        );

        tablaEquipos.getTableHeader().setForeground(Color.WHITE);

        tablaEquipos.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );

        tablaEquipos.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable tabla,
                            Object valor,
                            boolean seleccionado,
                            boolean enfocado,
                            int fila,
                            int columna
                    ) {

                        Component componente =
                                super.getTableCellRendererComponent(
                                        tabla,
                                        valor,
                                        seleccionado,
                                        enfocado,
                                        fila,
                                        columna
                                );

                        setBorder(
                                BorderFactory.createEmptyBorder(
                                        0, 8, 0, 8
                                )
                        );

                        if (columna == 0 ||
                                columna == 5 ||
                                columna == 6) {

                            setHorizontalAlignment(
                                    SwingConstants.CENTER
                            );

                        } else {
                            setHorizontalAlignment(
                                    SwingConstants.LEFT
                            );
                        }

                        if (seleccionado) {

                            componente.setBackground(PRIMARIO);
                            componente.setForeground(Color.WHITE);

                        } else {

                            componente.setBackground(
                                    fila % 2 == 0
                                            ? Color.WHITE
                                            : new Color(
                                            248,
                                            250,
                                            252
                                    )
                            );

                            componente.setForeground(TEXTO);

                            if (columna == 6 &&
                                    valor != null) {

                                String estado =
                                        valor.toString()
                                                .toLowerCase();

                                if (estado.contains("activo")) {
                                    componente.setForeground(
                                            new Color(
                                                    22,
                                                    163,
                                                    74
                                            )
                                    );

                                } else if (
                                        estado.contains(
                                                "mantenimiento"
                                        )
                                ) {
                                    componente.setForeground(
                                            new Color(
                                                    234,
                                                    88,
                                                    12
                                            )
                                    );

                                } else if (
                                        estado.contains("fuera")
                                ) {
                                    componente.setForeground(
                                            new Color(
                                                    220,
                                                    38,
                                                    38
                                            )
                                    );
                                }
                            }
                        }

                        return componente;
                    }
                }
        );

        TableColumnModel columnas =
                tablaEquipos.getColumnModel();

        columnas.getColumn(0).setPreferredWidth(45);
        columnas.getColumn(1).setPreferredWidth(90);
        columnas.getColumn(2).setPreferredWidth(100);
        columnas.getColumn(3).setPreferredWidth(125);
        columnas.getColumn(4).setPreferredWidth(150);
        columnas.getColumn(5).setPreferredWidth(145);
        columnas.getColumn(6).setPreferredWidth(135);
        columnas.getColumn(7).setPreferredWidth(160);
    }

    private JPanel crearTarjeta(
            LayoutManager layout
    ) {

        JPanel tarjeta = new JPanel(layout);
        tarjeta.setBackground(TARJETA);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                BORDE,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                18, 20, 18, 20
                        )
                )
        );

        return tarjeta;
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

        txtTipo.requestFocusInWindow();
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
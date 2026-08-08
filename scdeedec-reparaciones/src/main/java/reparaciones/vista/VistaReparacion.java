package reparaciones.vista;

import equipos.modelo.EquipoItem;
import usuarios.modelo.UsuarioItem;
import util.DatePickerField;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaReparacion extends JFrame {

    private JTextField txtFallaReportada;
    private JTextArea txtDiagnostico;
    private JTextArea txtSolucion;
    private JTextArea txtObservaciones;
    private JTextField txtCosto;
    private JTextField txtIdBuscar;

    private DatePickerField txtFechaIngreso;
    private DatePickerField txtFechaEntrega;

    private JComboBox<EquipoItem> cmbEquipo;
    private JComboBox<UsuarioItem> cmbTecnico;
    private JComboBox<String> cmbEstado;

    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnConsultar;
    private JButton btnLimpiar;
    private JButton btnEliminar;
    private JButton btnBuscarEquipo;

    private JTable tablaReparaciones;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private static final Color COLOR_FONDO = new Color(244, 247, 251);
    private static final Color COLOR_TARJETA = Color.WHITE;
    private static final Color COLOR_TEXTO = new Color(30, 41, 59);
    private static final Color COLOR_SECUNDARIO = new Color(100, 116, 139);
    private static final Color COLOR_BORDE = new Color(226, 232, 240);
    private static final Color COLOR_AZUL = new Color(37, 99, 235);
    private static final Color COLOR_VERDE = new Color(22, 163, 74);
    private static final Color COLOR_ROJO = new Color(220, 38, 38);
    private static final Color COLOR_NARANJA = new Color(234, 88, 12);

    public VistaReparacion() {

        setTitle("Gestión de reparaciones");
        setSize(1220, 950);
        setMinimumSize(new Dimension(1040, 660));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 18));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        panelPrincipal.add(crearEncabezado(), BorderLayout.NORTH);

        JScrollPane scrollContenido = new JScrollPane(crearContenido());
        scrollContenido.setBorder(null);
        scrollContenido.getVerticalScrollBar().setUnitIncrement(16);
        scrollContenido.getViewport().setOpaque(false);
        scrollContenido.setOpaque(false);

        panelPrincipal.add(scrollContenido, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
        getRootPane().setDefaultButton(btnRegistrar);
        setVisible(true);
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(0, 12));
        contenido.setOpaque(false);
        contenido.add(crearFormulario(), BorderLayout.NORTH);
        contenido.add(crearPanelTabla(), BorderLayout.CENTER);
        return contenido;
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);

        JLabel titulo = new JLabel("Gestión de reparaciones");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Registrá reparaciones y consultá el historial de cada equipo");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(COLOR_SECUNDARIO);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(4));
        encabezado.add(subtitulo);

        return encabezado;
    }

    private JPanel crearFormulario() {
        JPanel tarjeta = crearTarjeta(new BorderLayout(0, 10));

        JPanel encabezadoFormulario = new JPanel();
        encabezadoFormulario.setLayout(new BoxLayout(encabezadoFormulario, BoxLayout.Y_AXIS));
        encabezadoFormulario.setOpaque(false);

        JLabel titulo = new JLabel("Nueva reparación");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descripcion = new JLabel("Seleccioná el equipo, el técnico y completá los datos de la reparación");
        descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descripcion.setForeground(COLOR_SECUNDARIO);
        descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        encabezadoFormulario.add(titulo);
        encabezadoFormulario.add(Box.createVerticalStrut(3));
        encabezadoFormulario.add(descripcion);

        tarjeta.add(encabezadoFormulario, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);

        cmbEquipo = new JComboBox<>();
        estilizarEntrada(cmbEquipo);

        cmbTecnico = new JComboBox<>();
        estilizarEntrada(cmbTecnico);

        txtFallaReportada = crearCampoTexto("Describí la falla reportada por el usuario");

        cmbEstado = new JComboBox<>(new String[]{
                "Pendiente", "En reparación", "Reparado", "No reparable"
        });
        estilizarEntrada(cmbEstado);

        txtFechaIngreso = new DatePickerField("dd/MM/yyyy");
        txtFechaEntrega = new DatePickerField("dd/MM/yyyy (opcional)");
        txtCosto = crearCampoTexto("0.00");

        txtDiagnostico = crearAreaTexto();
        txtSolucion = crearAreaTexto();
        txtObservaciones = crearAreaTexto();

        int fila = 0;
        agregarCampo(campos, "Equipo", cmbEquipo, 0, fila, 1);
        agregarCampo(campos, "Técnico responsable", cmbTecnico, 1, fila, 1);

        fila++;
        agregarCampo(campos, "Falla reportada", txtFallaReportada, 0, fila, 2);

        fila++;
        agregarCampo(campos, "Fecha de ingreso", txtFechaIngreso, 0, fila, 1);
        agregarCampo(campos, "Fecha de entrega", txtFechaEntrega, 1, fila, 1);

        fila++;
        agregarCampo(campos, "Estado", cmbEstado, 0, fila, 1);
        agregarCampo(campos, "Costo", txtCosto, 1, fila, 1);

        fila++;
        agregarCampo(campos, "Diagnóstico (opcional)", crearScrollArea(txtDiagnostico), 0, fila, 1);
        agregarCampo(campos, "Solución (opcional)", crearScrollArea(txtSolucion), 1, fila, 1);

        fila++;
        agregarCampo(campos, "Observaciones (opcional)", crearScrollArea(txtObservaciones), 0, fila, 2);

        tarjeta.add(campos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);

        btnLimpiar = crearBoton("Limpiar", new Color(100, 116, 139), 110);
        btnBuscarEquipo = crearBoton("Buscar por equipo", COLOR_AZUL, 155);
        btnActualizar = crearBoton("Actualizar", new Color(8, 145, 178), 120);
        btnRegistrar = crearBoton("Registrar", COLOR_VERDE, 120);

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnBuscarEquipo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnRegistrar);

        tarjeta.add(panelBotones, BorderLayout.SOUTH);

        return tarjeta;
    }

    private JPanel crearPanelTabla() {
        JPanel tarjeta = crearTarjeta(new BorderLayout(0, 14));

        JPanel parteSuperior = new JPanel(new BorderLayout(0, 12));
        parteSuperior.setOpaque(false);

        JPanel encabezadoTabla = new JPanel(new BorderLayout());
        encabezadoTabla.setOpaque(false);

        JLabel titulo = new JLabel("Historial de reparaciones");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(COLOR_TEXTO);

        lblTotal = new JLabel("Total: 0 reparaciones");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotal.setForeground(COLOR_NARANJA);
        lblTotal.setOpaque(true);
        lblTotal.setBackground(new Color(255, 247, 237));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));

        encabezadoTabla.add(titulo, BorderLayout.WEST);
        encabezadoTabla.add(lblTotal, BorderLayout.EAST);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelBusqueda.setOpaque(false);

        JLabel etiquetaBuscar = new JLabel("Buscar por ID:");
        etiquetaBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiquetaBuscar.setForeground(COLOR_TEXTO);

        txtIdBuscar = crearCampoTexto("Ej. 1");
        txtIdBuscar.setPreferredSize(new Dimension(130, 38));

        btnConsultar = crearBoton("Ver todos", new Color(8, 145, 178), 115);
        btnEliminar = crearBoton("Eliminar seleccionada", COLOR_ROJO, 175);

        panelBusqueda.add(etiquetaBuscar);
        panelBusqueda.add(txtIdBuscar);
        panelBusqueda.add(btnConsultar);
        panelBusqueda.add(btnEliminar);

        parteSuperior.add(encabezadoTabla, BorderLayout.NORTH);
        parteSuperior.add(panelBusqueda, BorderLayout.SOUTH);

        tarjeta.add(parteSuperior, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Equipo", "Falla reportada", "Fecha ingreso", "Fecha entrega", "Estado", "Costo", "Técnico"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReparaciones = new JTable(modeloTabla);
        tablaReparaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaReparaciones.setRowHeight(36);
        tablaReparaciones.setFillsViewportHeight(true);
        tablaReparaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaReparaciones.setShowVerticalLines(false);
        tablaReparaciones.setShowHorizontalLines(true);
        tablaReparaciones.setGridColor(new Color(241, 245, 249));
        tablaReparaciones.setIntercellSpacing(new Dimension(0, 1));
        tablaReparaciones.setAutoCreateRowSorter(true);

        tablaReparaciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaReparaciones.getTableHeader().setBackground(new Color(15, 23, 42));
        tablaReparaciones.getTableHeader().setForeground(Color.WHITE);
        tablaReparaciones.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tablaReparaciones.getTableHeader().setReorderingAllowed(false);

        tablaReparaciones.setDefaultRenderer(Object.class, crearRenderizadorTabla());

        configurarColumnas();

        JScrollPane scrollTabla = new JScrollPane(tablaReparaciones);
        scrollTabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setPreferredSize(new Dimension(100, 320));

        tarjeta.add(scrollTabla, BorderLayout.CENTER);

        return tarjeta;
    }

    private void agregarCampo(JPanel panel, String texto, JComponent componente, int columna, int fila, int ancho) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.weightx = ancho == 2 ? 1.0 : 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(4, columna == 0 ? 0 : 8, 4, columna + ancho >= 2 ? 0 : 8);

        panel.add(crearGrupoCampo(texto, componente), gbc);
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.putClientProperty("JTextField.placeholderText", placeholder);
        estilizarEntrada(campo);
        return campo;
    }

    private JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea(2, 30);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return area;
    }

    private JScrollPane crearScrollArea(JTextArea area) {
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(100, 46));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scroll;
    }

    private JPanel crearGrupoCampo(String texto, JComponent componente) {
        JPanel grupo = new JPanel();
        grupo.setLayout(new BoxLayout(grupo, BoxLayout.Y_AXIS));
        grupo.setOpaque(false);

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 13));
        etiqueta.setForeground(COLOR_TEXTO);
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);

        componente.setAlignmentX(Component.LEFT_ALIGNMENT);

        grupo.add(etiqueta);
        grupo.add(Box.createVerticalStrut(6));
        grupo.add(componente);

        return grupo;
    }

    private void estilizarEntrada(JComponent componente) {
        componente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        componente.setPreferredSize(new Dimension(100, 38));
        componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        componente.putClientProperty("JComponent.roundRect", true);
    }

    private JButton crearBoton(String texto, Color color, int ancho) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(ancho, 38));
        boton.putClientProperty("JButton.buttonType", "roundRect");
        return boton;
    }

    private JPanel crearTarjeta(LayoutManager layout) {
        JPanel tarjeta = new JPanel(layout);
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)
        ));
        return tarjeta;
    }

    private DefaultTableCellRenderer crearRenderizadorTabla() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                             boolean hasFocus, int row, int column) {
                Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    componente.setBackground(new Color(255, 237, 213));
                    componente.setForeground(new Color(15, 23, 42));
                } else {
                    componente.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));

                    if (column == 5 && value != null) {
                        String estado = value.toString().toLowerCase();
                        if (estado.contains("pendiente")) {
                            componente.setForeground(COLOR_NARANJA);
                        } else if (estado.contains("en reparación")) {
                            componente.setForeground(COLOR_AZUL);
                        } else if (estado.contains("reparado")) {
                            componente.setForeground(COLOR_VERDE);
                        } else if (estado.contains("no reparable")) {
                            componente.setForeground(COLOR_ROJO);
                        } else {
                            componente.setForeground(COLOR_TEXTO);
                        }
                    } else {
                        componente.setForeground(COLOR_TEXTO);
                    }
                }

                if (componente instanceof JComponent) {
                    ((JComponent) componente).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                }

                return componente;
            }
        };
    }

    private void configurarColumnas() {
        tablaReparaciones.getColumnModel().getColumn(0).setPreferredWidth(45);
        tablaReparaciones.getColumnModel().getColumn(1).setPreferredWidth(160);
        tablaReparaciones.getColumnModel().getColumn(2).setPreferredWidth(230);
        tablaReparaciones.getColumnModel().getColumn(3).setPreferredWidth(95);
        tablaReparaciones.getColumnModel().getColumn(4).setPreferredWidth(95);
        tablaReparaciones.getColumnModel().getColumn(5).setPreferredWidth(110);
        tablaReparaciones.getColumnModel().getColumn(6).setPreferredWidth(90);
        tablaReparaciones.getColumnModel().getColumn(7).setPreferredWidth(150);
    }

    // Getters utilizados por ReparacionController

    public JComboBox<EquipoItem> getCmbEquipo() { return cmbEquipo; }
    public JComboBox<UsuarioItem> getCmbTecnico() { return cmbTecnico; }
    public JComboBox<String> getCmbEstado() { return cmbEstado; }
    public JTextField getTxtFallaReportada() { return txtFallaReportada; }
    public JTextArea getTxtDiagnostico() { return txtDiagnostico; }
    public JTextArea getTxtSolucion() { return txtSolucion; }
    public JTextArea getTxtObservaciones() { return txtObservaciones; }
    public DatePickerField getTxtFechaIngreso() { return txtFechaIngreso; }
    public DatePickerField getTxtFechaEntrega() { return txtFechaEntrega; }
    public JTextField getTxtCosto() { return txtCosto; }
    public JTextField getTxtIdBuscar() { return txtIdBuscar; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnConsultar() { return btnConsultar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnBuscarEquipo() { return btnBuscarEquipo; }
    public JTable getTablaReparaciones() { return tablaReparaciones; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JLabel getLblTotal() { return lblTotal; }

    public void limpiarCampos() {
        cmbEquipo.setSelectedIndex(-1);
        cmbTecnico.setSelectedIndex(-1);
        cmbEstado.setSelectedIndex(0);

        txtFallaReportada.setText("");
        txtDiagnostico.setText("");
        txtSolucion.setText("");
        txtObservaciones.setText("");
        txtFechaIngreso.setText("");
        txtFechaEntrega.setText("");
        txtCosto.setText("");
        txtIdBuscar.setText("");

        tablaReparaciones.clearSelection();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación completada", JOptionPane.INFORMATION_MESSAGE);
    }
}

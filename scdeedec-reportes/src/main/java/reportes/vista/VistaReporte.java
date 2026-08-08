package reportes.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import util.DatePickerField;

public class VistaReporte extends JFrame {

    private JComboBox<String> cmbTipoReporte;
    private JTextField txtDesde;
    private JTextField txtHasta;
    private JButton btnCalendarioDesde;
    private JButton btnCalendarioHasta;
    private JButton btnGenerar;

    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotal;

    private static final Color COLOR_FONDO = new Color(244, 247, 251);
    private static final Color COLOR_TARJETA = Color.WHITE;
    private static final Color COLOR_TEXTO = new Color(30, 41, 59);
    private static final Color COLOR_SECUNDARIO = new Color(100, 116, 139);
    private static final Color COLOR_BORDE = new Color(226, 232, 240);
    private static final Color COLOR_AZUL = new Color(37, 99, 235);
    private JButton btnExportar;

    public VistaReporte() {

        setTitle("Reportes");
        setSize(1120, 720);
        setMinimumSize(new Dimension(950, 620));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 18));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        panelPrincipal.add(crearEncabezado(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(0, 18));
        contenido.setOpaque(false);

        contenido.add(crearFiltros(), BorderLayout.NORTH);
        contenido.add(crearPanelTabla(), BorderLayout.CENTER);

        panelPrincipal.add(contenido, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
        getRootPane().setDefaultButton(btnGenerar);

        setVisible(true);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);

        JLabel titulo = new JLabel("Reportes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(COLOR_TEXTO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Consultá el estado del inventario, asignaciones, mantenimientos y reparaciones");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(COLOR_SECUNDARIO);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(4));
        encabezado.add(subtitulo);

        return encabezado;
    }

    private JPanel crearFiltros() {
        JPanel tarjeta = crearTarjeta(new BorderLayout(0, 18));

        JLabel titulo = new JLabel("Seleccioná el reporte");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(COLOR_TEXTO);
        tarjeta.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(1, 3, 16, 14));
        campos.setOpaque(false);

        cmbTipoReporte = new JComboBox<>(new String[]{
                "Inventario por Estado",
                "Equipos por Usuario",
                "Mantenimientos por Equipo",
                "Reparaciones por Equipo",
                "Resumen General"
        });
        estilizarEntrada(cmbTipoReporte);

        txtDesde = crearCampoTexto("YYYY-MM-DD");
        txtHasta = crearCampoTexto("YYYY-MM-DD");
        btnCalendarioDesde = DatePickerField.crearBotonCalendario(txtDesde, "yyyy-MM-dd");
        btnCalendarioHasta = DatePickerField.crearBotonCalendario(txtHasta, "yyyy-MM-dd");
        habilitarFiltroFechas(false);

        campos.add(crearGrupoCampo("Tipo de reporte", cmbTipoReporte));
        campos.add(crearGrupoCampo("Desde", envolverConCalendario(txtDesde, btnCalendarioDesde)));
        campos.add(crearGrupoCampo("Hasta", envolverConCalendario(txtHasta, btnCalendarioHasta)));

        tarjeta.add(campos, BorderLayout.CENTER);

        btnGenerar = crearBoton("Generar reporte", COLOR_AZUL);
        btnExportar = crearBoton("Exportar CSV", new Color(22, 163, 74));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnExportar);
        panelBotones.add(btnGenerar);

        tarjeta.add(panelBotones, BorderLayout.SOUTH);

        return tarjeta;
    }

    private JPanel crearPanelTabla() {
        JPanel tarjeta = crearTarjeta(new BorderLayout(0, 14));

        JPanel encabezadoTabla = new JPanel(new BorderLayout());
        encabezadoTabla.setOpaque(false);

        JLabel titulo = new JLabel("Resultados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(COLOR_TEXTO);

        lblTotal = new JLabel("Total: 0 filas");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotal.setForeground(COLOR_AZUL);
        lblTotal.setOpaque(true);
        lblTotal.setBackground(new Color(239, 246, 255));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));

        encabezadoTabla.add(titulo, BorderLayout.WEST);
        encabezadoTabla.add(lblTotal, BorderLayout.EAST);

        tarjeta.add(encabezadoTabla, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReportes = new JTable(modeloTabla);
        tablaReportes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaReportes.setRowHeight(36);
        tablaReportes.setFillsViewportHeight(true);
        tablaReportes.setShowVerticalLines(false);
        tablaReportes.setShowHorizontalLines(true);
        tablaReportes.setGridColor(new Color(241, 245, 249));
        tablaReportes.setIntercellSpacing(new Dimension(0, 1));
        tablaReportes.setAutoCreateRowSorter(true);

        tablaReportes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaReportes.getTableHeader().setBackground(new Color(15, 23, 42));
        tablaReportes.getTableHeader().setForeground(Color.WHITE);
        tablaReportes.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tablaReportes.getTableHeader().setReorderingAllowed(false);

        tablaReportes.setDefaultRenderer(Object.class, crearRenderizadorTabla());

        JScrollPane scrollTabla = new JScrollPane(tablaReportes);
        scrollTabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        scrollTabla.getViewport().setBackground(Color.WHITE);

        tarjeta.add(scrollTabla, BorderLayout.CENTER);

        return tarjeta;
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.putClientProperty("JTextField.placeholderText", placeholder);
        estilizarEntrada(campo);
        return campo;
    }

    /** Envuelve el campo de fecha + su botón de calendario en un solo panel para el grid del formulario. */
    private JPanel envolverConCalendario(JTextField campo, JButton botonCalendario) {
        JPanel contenedor = new JPanel(new BorderLayout(6, 0));
        contenedor.setOpaque(false);
        contenedor.add(campo, BorderLayout.CENTER);
        contenedor.add(botonCalendario, BorderLayout.EAST);
        return contenedor;
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

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(150, 38));
        boton.putClientProperty("JButton.buttonType", "roundRect");
        return boton;
    }

    private JPanel crearTarjeta(LayoutManager layout) {
        JPanel tarjeta = new JPanel(layout);
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
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
                    componente.setBackground(new Color(219, 234, 254));
                    componente.setForeground(new Color(15, 23, 42));
                } else {
                    componente.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                    componente.setForeground(COLOR_TEXTO);
                }

                if (componente instanceof JComponent) {
                    ((JComponent) componente).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                }

                return componente;
            }
        };
    }

    /**
     * Activa o desactiva los campos de fecha. El Controller los desactiva
     * cuando el reporte elegido no usa fechas (ej. Inventario, Resumen General).
     */
    public void habilitarFiltroFechas(boolean habilitado) {
        txtDesde.setEnabled(habilitado);
        txtHasta.setEnabled(habilitado);
        btnCalendarioDesde.setEnabled(habilitado);
        btnCalendarioHasta.setEnabled(habilitado);
    }

    // Getters que utiliza ReporteController

    public JComboBox<String> getCmbTipoReporte() {
        return cmbTipoReporte;
    }

    public JTextField getTxtDesde() {
        return txtDesde;
    }

    public JTextField getTxtHasta() {
        return txtHasta;
    }

    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    public JButton getBtnExportar() {
        return btnExportar;
    }

    public JTable getTablaReportes() {
        return tablaReportes;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación completada", JOptionPane.INFORMATION_MESSAGE);
    }
}
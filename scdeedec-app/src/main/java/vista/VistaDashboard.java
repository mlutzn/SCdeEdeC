package vista;

import dashboard.modelo.ResumenDashboard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VistaDashboard extends JPanel {

    private static final Color FONDO =
            new Color(244, 247, 251);

    private static final Color BARRA_LATERAL =
            new Color(15, 23, 42);

    private static final Color BARRA_HOVER =
            new Color(30, 41, 59);

    private static final Color TEXTO =
            new Color(30, 41, 59);

    private static final Color TEXTO_SECUNDARIO =
            new Color(100, 116, 139);

    private final JLabel lblTotalEquipos;
    private final JLabel lblTotalUsuarios;
    private final JLabel lblTotalMantenimientos;
    private final JLabel lblTotalReparaciones;
    private final JLabel lblReparacionesPendientes;

    private JButton btnActualizarResumen;
    private JButton btnEquipos;
    private JButton btnUsuarios;
    private JButton btnMantenimientos;
    private JButton btnReparaciones;
    private JButton btnReportes;
    private JButton btnSalir;

    public VistaDashboard() {

        lblTotalEquipos = crearEtiquetaCantidad();
        lblTotalUsuarios = crearEtiquetaCantidad();
        lblTotalMantenimientos = crearEtiquetaCantidad();
        lblTotalReparaciones = crearEtiquetaCantidad();
        lblReparacionesPendientes = crearEtiquetaCantidad();

        setLayout(new BorderLayout());
        setBackground(FONDO);

        add(crearBarraLateral(), BorderLayout.WEST);
        add(crearAreaPrincipal(), BorderLayout.CENTER);
    }

    private JPanel crearBarraLateral() {

        JPanel barra = new JPanel(new BorderLayout());
        barra.setBackground(BARRA_LATERAL);
        barra.setPreferredSize(new Dimension(245, 0));

        JPanel encabezado = new JPanel();
        encabezado.setOpaque(false);
        encabezado.setLayout(
                new BoxLayout(encabezado, BoxLayout.Y_AXIS)
        );

        encabezado.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 24, 25, 24
                )
        );

        JLabel marca = new JLabel("SCdeEdeC");
        marca.setForeground(Color.WHITE);
        marca.setFont(
                new Font("SansSerif", Font.BOLD, 24)
        );

        JLabel descripcion =
                new JLabel("Control de equipos");

        descripcion.setForeground(
                new Color(148, 163, 184)
        );

        descripcion.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        encabezado.add(marca);
        encabezado.add(Box.createVerticalStrut(5));
        encabezado.add(descripcion);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(
                new BoxLayout(menu, BoxLayout.Y_AXIS)
        );

        menu.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 12, 10, 12
                )
        );

        JLabel lblMenu = new JLabel("MENÚ PRINCIPAL");
        lblMenu.setForeground(
                new Color(100, 116, 139)
        );

        lblMenu.setFont(
                new Font("SansSerif", Font.BOLD, 11)
        );

        lblMenu.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 12, 12, 0
                )
        );

        btnEquipos = crearBotonMenu(
                "Gestión de equipos"
        );

        btnUsuarios = crearBotonMenu(
                "Gestión de usuarios"
        );

        btnMantenimientos = crearBotonMenu(
                "Mantenimientos"
        );

        btnReparaciones = crearBotonMenu(
                "Reparaciones"
        );

        btnReportes = crearBotonMenu(
                "Reportes"
        );

        menu.add(lblMenu);
        menu.add(btnEquipos);
        menu.add(Box.createVerticalStrut(7));
        menu.add(btnUsuarios);
        menu.add(Box.createVerticalStrut(7));
        menu.add(btnMantenimientos);
        menu.add(Box.createVerticalStrut(7));
        menu.add(btnReparaciones);
        menu.add(Box.createVerticalStrut(7));
        menu.add(btnReportes);

        JPanel panelSalir = new JPanel(
                new BorderLayout()
        );

        panelSalir.setOpaque(false);

        panelSalir.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 12, 25, 12
                )
        );

        btnSalir = crearBotonMenu("Cerrar sesión");
        btnSalir.setForeground(
                new Color(252, 165, 165)
        );

        panelSalir.add(btnSalir);

        barra.add(encabezado, BorderLayout.NORTH);
        barra.add(menu, BorderLayout.CENTER);
        barra.add(panelSalir, BorderLayout.SOUTH);

        return barra;
    }

    private JButton crearBotonMenu(String texto) {

        JButton boton = new JButton(texto);

        boton.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        boton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        boton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 48)
        );

        boton.setPreferredSize(
                new Dimension(220, 48)
        );

        boton.setBackground(BARRA_LATERAL);
        boton.setForeground(
                new Color(226, 232, 240)
        );

        boton.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        boton.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 18, 0, 12
                )
        );

        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);

        boton.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        boton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(BARRA_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(BARRA_LATERAL);
            }
        });

        return boton;
    }

    private JPanel crearAreaPrincipal() {

        JPanel principal = new JPanel(
                new BorderLayout(0, 25)
        );

        principal.setBackground(FONDO);

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        28, 30, 30, 30
                )
        );

        principal.add(
                crearEncabezado(),
                BorderLayout.NORTH
        );

        JPanel contenido = new JPanel(
                new BorderLayout(0, 25)
        );

        contenido.setOpaque(false);

        contenido.add(
                crearPanelTarjetas(),
                BorderLayout.NORTH
        );

        contenido.add(
                crearPanelBienvenida(),
                BorderLayout.CENTER
        );

        principal.add(contenido, BorderLayout.CENTER);

        return principal;
    }

    private JPanel crearEncabezado() {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(
                new BoxLayout(textos, BoxLayout.Y_AXIS)
        );

        JLabel titulo =
                new JLabel("Panel de control");

        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );

        titulo.setForeground(TEXTO);

        JLabel subtitulo = new JLabel(
                "Resumen general del sistema"
        );

        subtitulo.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        subtitulo.setForeground(TEXTO_SECUNDARIO);

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(subtitulo);

        btnActualizarResumen =
                new JButton("Actualizar datos");

        btnActualizarResumen.setBackground(
                new Color(37, 99, 235)
        );

        btnActualizarResumen.setForeground(Color.WHITE);
        btnActualizarResumen.setFocusPainted(false);
        btnActualizarResumen.setBorderPainted(false);

        btnActualizarResumen.setPreferredSize(
                new Dimension(155, 42)
        );

        btnActualizarResumen.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        btnActualizarResumen.putClientProperty(
                "JButton.buttonType",
                "roundRect"
        );

        panel.add(textos, BorderLayout.WEST);
        panel.add(
                btnActualizarResumen,
                BorderLayout.EAST
        );

        return panel;
    }

    private JPanel crearPanelTarjetas() {

        JPanel panel = new JPanel(
                new GridLayout(1, 5, 12, 0)
        );

        panel.setOpaque(false);

        panel.setPreferredSize(
                new Dimension(0, 125)
        );

        panel.add(crearTarjeta(
                "Equipos",
                lblTotalEquipos,
                new Color(37, 99, 235)
        ));

        panel.add(crearTarjeta(
                "Usuarios",
                lblTotalUsuarios,
                new Color(124, 58, 237)
        ));

        panel.add(crearTarjeta(
                "Mantenimientos",
                lblTotalMantenimientos,
                new Color(234, 88, 12)
        ));

        panel.add(crearTarjeta(
                "Reparaciones",
                lblTotalReparaciones,
                new Color(220, 38, 38)
        ));

        panel.add(crearTarjeta(
                "<html>Reparaciones<br>pendientes</html>",
                lblReparacionesPendientes,
                new Color(202, 138, 4)
        ));

        return panel;
    }

    private JPanel crearTarjeta(
            String titulo,
            JLabel cantidad,
            Color color
    ) {

        JPanel tarjeta = new JPanel(
                new BorderLayout()
        );

        tarjeta.setBackground(Color.WHITE);

        tarjeta.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(226, 232, 240),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 16, 15, 16
                        )
                )
        );

        JPanel barraColor = new JPanel();
        barraColor.setBackground(color);

        barraColor.setPreferredSize(
                new Dimension(0, 5)
        );

        JPanel informacion = new JPanel();
        informacion.setOpaque(false);

        informacion.setLayout(
                new BoxLayout(
                        informacion,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setForeground(TEXTO_SECUNDARIO);
        lblTitulo.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        informacion.add(lblTitulo);
        informacion.add(Box.createVerticalGlue());
        informacion.add(cantidad);

        tarjeta.add(barraColor, BorderLayout.NORTH);
        tarjeta.add(informacion, BorderLayout.CENTER);

        return tarjeta;
    }

    private JPanel crearPanelBienvenida() {

        JPanel panel = new JPanel(
                new BorderLayout(30, 0)
        );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(226, 232, 240),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                30, 30, 30, 30
                        )
                )
        );

        JPanel textos = new JPanel();
        textos.setOpaque(false);

        textos.setLayout(
                new BoxLayout(textos, BoxLayout.Y_AXIS)
        );

        JLabel titulo = new JLabel(
                "Bienvenido al sistema"
        );

        titulo.setFont(
                new Font("SansSerif", Font.BOLD, 23)
        );

        titulo.setForeground(TEXTO);

        JLabel informacion = new JLabel(
                "<html>Utilice el menú lateral para administrar " +
                        "equipos, usuarios, mantenimientos, reparaciones " +
                        "y consultar los reportes generales.<br><br>" +
                        "Los datos del resumen pueden actualizarse " +
                        "con el botón superior.</html>"
        );

        informacion.setForeground(TEXTO_SECUNDARIO);
        informacion.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(15));
        textos.add(informacion);

        JPanel estado = new JPanel();
        estado.setBackground(
                new Color(236, 253, 245)
        );

        estado.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(167, 243, 208),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 22, 20, 22
                        )
                )
        );

        JLabel lblEstado = new JLabel(
                "<html><b>Sistema disponible</b><br>" +
                        "Conexión establecida correctamente</html>"
        );

        lblEstado.setForeground(
                new Color(6, 95, 70)
        );

        estado.add(lblEstado);

        panel.add(textos, BorderLayout.CENTER);
        panel.add(estado, BorderLayout.EAST);

        return panel;
    }

    private JLabel crearEtiquetaCantidad() {

        JLabel etiqueta = new JLabel("0");

        etiqueta.setFont(
                new Font("SansSerif", Font.BOLD, 29)
        );

        etiqueta.setForeground(TEXTO);

        return etiqueta;
    }

    public void mostrarResumen(
            ResumenDashboard resumen
    ) {
        lblTotalEquipos.setText(
                String.valueOf(resumen.getTotalEquipos())
        );

        lblTotalUsuarios.setText(
                String.valueOf(resumen.getTotalUsuarios())
        );

        lblTotalMantenimientos.setText(
                String.valueOf(
                        resumen.getTotalMantenimientos()
                )
        );

        lblTotalReparaciones.setText(
                String.valueOf(
                        resumen.getTotalReparaciones()
                )
        );

        lblReparacionesPendientes.setText(
                String.valueOf(
                        resumen.getReparacionesPendientes()
                )
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

    public JButton getBtnActualizarResumen() {
        return btnActualizarResumen;
    }

    public JButton getBtnEquipos() {
        return btnEquipos;
    }

    public JButton getBtnUsuarios() {
        return btnUsuarios;
    }

    public JButton getBtnMantenimientos() {
        return btnMantenimientos;
    }

    public JButton getBtnReparaciones() {
        return btnReparaciones;
    }

    public JButton getBtnReportes() {
        return btnReportes;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }
}
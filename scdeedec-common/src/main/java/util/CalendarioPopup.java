package util;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Popup de calendario reutilizable. Además de las flechas ◀ ▶ para moverse
 * de a un mes, tiene un combo de mes y un spinner de año para saltar directo
 * sin tener que hacer clic decenas de veces — pensado para pantallas como
 * Reportes, donde el historial puede cubrir varios años hacia atrás.
 */
public class CalendarioPopup {

    private static final String[] NOMBRES_MESES = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private final Consumer<Date> alSeleccionarDia;

    private JPopupMenu popup;
    private JComboBox<String> comboMes;
    private JSpinner spinnerAnio;
    private JPanel panelDias;
    private Calendar calendarioMostrado;
    private Date fechaSeleccionadaActual;
    private boolean actualizandoCabecera;

    public CalendarioPopup(Consumer<Date> alSeleccionarDia) {
        this.alSeleccionarDia = alSeleccionarDia;
    }

    /** Muestra el popup debajo del componente ancla, centrado en fechaActual (o en hoy si es null). */
    public void mostrar(Component ancla, Date fechaActual) {
        calendarioMostrado = Calendar.getInstance();
        fechaSeleccionadaActual = fechaActual;
        if (fechaActual != null) {
            calendarioMostrado.setTime(fechaActual);
        }

        popup = new JPopupMenu();
        JPanel contenedor = new JPanel(new BorderLayout(8, 8));
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contenedor.setBackground(Color.WHITE);

        contenedor.add(crearCabecera(), BorderLayout.NORTH);

        panelDias = new JPanel(new GridLayout(0, 7, 2, 2));
        panelDias.setOpaque(false);
        contenedor.add(panelDias, BorderLayout.CENTER);

        popup.add(contenedor);
        actualizarDias();

        popup.show(ancla, 0, ancla.getHeight());
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout(6, 0));
        cabecera.setOpaque(false);

        JButton anterior = crearBotonNavegacion("◀");
        JButton siguiente = crearBotonNavegacion("▶");

        comboMes = new JComboBox<>(NOMBRES_MESES);
        comboMes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboMes.addActionListener(e -> {
            if (actualizandoCabecera) return;
            calendarioMostrado.set(Calendar.MONTH, comboMes.getSelectedIndex());
            actualizarDias();
        });

        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        spinnerAnio = new JSpinner(new SpinnerNumberModel(anioActual, anioActual - 60, anioActual + 5, 1));
        spinnerAnio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JSpinner.NumberEditor editorAnio = new JSpinner.NumberEditor(spinnerAnio, "#");
        spinnerAnio.setEditor(editorAnio);
        editorAnio.getTextField().setColumns(4);
        spinnerAnio.addChangeListener(e -> {
            if (actualizandoCabecera) return;
            calendarioMostrado.set(Calendar.YEAR, (Integer) spinnerAnio.getValue());
            actualizarDias();
        });

        JPanel selectorMesAnio = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        selectorMesAnio.setOpaque(false);
        selectorMesAnio.add(comboMes);
        selectorMesAnio.add(spinnerAnio);

        anterior.addActionListener(e -> {
            calendarioMostrado.add(Calendar.MONTH, -1);
            actualizarDias();
        });
        siguiente.addActionListener(e -> {
            calendarioMostrado.add(Calendar.MONTH, 1);
            actualizarDias();
        });

        cabecera.add(anterior, BorderLayout.WEST);
        cabecera.add(selectorMesAnio, BorderLayout.CENTER);
        cabecera.add(siguiente, BorderLayout.EAST);

        return cabecera;
    }

    private JButton crearBotonNavegacion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void actualizarCabecera() {
        actualizandoCabecera = true;
        comboMes.setSelectedIndex(calendarioMostrado.get(Calendar.MONTH));
        spinnerAnio.setValue(calendarioMostrado.get(Calendar.YEAR));
        actualizandoCabecera = false;
    }

    private void actualizarDias() {
        actualizarCabecera();
        panelDias.removeAll();

        String[] nombresDias = {"L", "M", "X", "J", "V", "S", "D"};
        for (String nombre : nombresDias) {
            JLabel etiqueta = new JLabel(nombre, SwingConstants.CENTER);
            etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 11));
            etiqueta.setForeground(new Color(100, 116, 139));
            panelDias.add(etiqueta);
        }

        Calendar primerDiaMes = (Calendar) calendarioMostrado.clone();
        primerDiaMes.set(Calendar.DAY_OF_MONTH, 1);

        int diaSemanaInicio = primerDiaMes.get(Calendar.DAY_OF_WEEK);
        // Calendar.DAY_OF_WEEK: domingo=1 ... sábado=7. La grilla arranca en lunes.
        int espaciosVacios = (diaSemanaInicio == Calendar.SUNDAY) ? 6 : diaSemanaInicio - 2;
        for (int i = 0; i < espaciosVacios; i++) {
            panelDias.add(new JLabel(""));
        }

        int diasEnMes = primerDiaMes.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar hoy = Calendar.getInstance();

        for (int dia = 1; dia <= diasEnMes; dia++) {
            Calendar fechaDelBoton = (Calendar) primerDiaMes.clone();
            fechaDelBoton.set(Calendar.DAY_OF_MONTH, dia);

            JButton botonDia = new JButton(String.valueOf(dia));
            botonDia.setFocusPainted(false);
            botonDia.setMargin(new Insets(2, 2, 2, 2));
            botonDia.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            botonDia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            boolean esSeleccionado = fechaSeleccionadaActual != null
                    && mismoDia(fechaDelBoton, dateACalendar(fechaSeleccionadaActual));
            boolean esHoy = mismoDia(fechaDelBoton, hoy);

            if (esSeleccionado) {
                botonDia.setOpaque(true);
                botonDia.setBorderPainted(false);
                botonDia.setBackground(new Color(37, 99, 235));
                botonDia.setForeground(Color.WHITE);
            } else if (esHoy) {
                botonDia.setForeground(new Color(37, 99, 235));
            }

            final Date fechaElegida = fechaDelBoton.getTime();
            botonDia.addActionListener(e -> {
                fechaSeleccionadaActual = fechaElegida;
                if (popup != null) {
                    popup.setVisible(false);
                }
                alSeleccionarDia.accept(fechaElegida);
            });

            panelDias.add(botonDia);
        }

        panelDias.revalidate();
        panelDias.repaint();

        if (popup != null) {
            popup.pack();
        }
    }

    private boolean mismoDia(Calendar a, Calendar b) {
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR);
    }

    private Calendar dateACalendar(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return c;
    }
}

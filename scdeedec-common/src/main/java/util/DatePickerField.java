package util;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Campo de texto (formato dd/MM/yyyy) con un botón que despliega un mini
 * calendario para elegir la fecha con el mouse. También se puede escribir
 * la fecha a mano, igual que un JTextField común.
 */
public class DatePickerField extends JPanel {

    private final JTextField campoTexto;
    private final JButton botonCalendario;
    private final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public DatePickerField() {
        this(null);
    }

    public DatePickerField(String placeholder) {
        super(new BorderLayout(4, 0));
        setOpaque(false);

        formato.setLenient(false);

        campoTexto = new JTextField();
        if (placeholder != null) {
            campoTexto.putClientProperty("JTextField.placeholderText", placeholder);
        }
        campoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        botonCalendario = DatePickerField.crearBotonCalendario(campoTexto, "dd/MM/yyyy");

        add(campoTexto, BorderLayout.CENTER);
        add(botonCalendario, BorderLayout.EAST);

        setPreferredSize(new Dimension(100, 38));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (campoTexto != null) campoTexto.setEnabled(enabled);
        if (botonCalendario != null) botonCalendario.setEnabled(enabled);
    }

    public String getText() {
        return campoTexto.getText();
    }

    public void setText(String texto) {
        campoTexto.setText(texto);
    }

    public Date getFecha() {
        String texto = campoTexto.getText().trim();
        if (texto.isEmpty()) {
            return null;
        }
        try {
            return formato.parse(texto);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setFecha(Date fecha) {
        campoTexto.setText(fecha != null ? formato.format(fecha) : "");
    }

    public JTextField getCampoTexto() {
        return campoTexto;
    }

    /**
     * Crea un botón de calendario independiente que escribe la fecha elegida
     * directamente en un JTextField ya existente, con el patrón indicado.
     * Pensado para pantallas donde el campo de fecha necesita seguir siendo
     * un JTextField normal (ej. Reportes, que lo lee con java.time.LocalDate
     * en formato ISO yyyy-MM-dd) y no conviene cambiarle el tipo.
     */
    public static JButton crearBotonCalendario(JTextField campoDestino, String patronFecha) {
        SimpleDateFormat formatoCampo = new SimpleDateFormat(patronFecha);
        formatoCampo.setLenient(false);

        JButton boton = new JButton("📅");
        boton.setFocusPainted(false);
        boton.setMargin(new Insets(2, 6, 2, 6));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setToolTipText("Elegir fecha del calendario");

        boton.addActionListener(e -> {
            Date fechaActual = null;
            String texto = campoDestino.getText().trim();
            if (!texto.isEmpty()) {
                try {
                    fechaActual = formatoCampo.parse(texto);
                } catch (ParseException ignorada) {
                    // Si lo que hay tipeado no es una fecha válida, el calendario simplemente abre en el mes actual
                }
            }

            new CalendarioPopup(fecha -> campoDestino.setText(formatoCampo.format(fecha)))
                    .mostrar(boton, fechaActual);
        });

        return boton;
    }
}

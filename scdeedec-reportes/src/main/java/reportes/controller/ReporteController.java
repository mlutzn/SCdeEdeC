package reportes.controller;

import reportes.modelo.Reporte;
import reportes.service.ReporteService;
import reportes.vista.VistaReporte;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;
import java.io.IOException;

public class ReporteController {
    private final ReporteService service = new ReporteService();
    private final VistaReporte vista;
    private Reporte reporteActual;

    public ReporteController(VistaReporte vista) {
        this.vista = vista;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnGenerar().addActionListener(e -> generarReporte());

        vista.getCmbTipoReporte().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                actualizarEstadoFiltroFechas();
            }

        });

        vista.getBtnExportar().addActionListener(e -> exportarCSV());
    }

    /**
     * Solo Mantenimientos y Reparaciones tienen campo de fecha en la vista SQL,
     * así que el resto de reportes mantiene los campos "Desde"/"Hasta" apagados.
     */
    private void actualizarEstadoFiltroFechas() {
        String seleccion = (String) vista.getCmbTipoReporte().getSelectedItem();
        boolean usaFechas = "Mantenimientos por Equipo".equals(seleccion)
                || "Reparaciones por Equipo".equals(seleccion);
        vista.habilitarFiltroFechas(usaFechas);
    }

    private void generarReporte() {
        try {
            String seleccion = (String) vista.getCmbTipoReporte().getSelectedItem();
            Reporte reporte;

            switch (seleccion) {
                case "Inventario por Estado":
                    reporte = service.obtenerInventarioPorEstado();
                    break;
                case "Equipos por Usuario":
                    reporte = service.obtenerEquiposPorUsuario();
                    break;
                case "Mantenimientos por Equipo":
                    reporte = service.obtenerMantenimientosPorEquipo(
                            leerFecha(vista.getTxtDesde()),
                            leerFecha(vista.getTxtHasta())
                    );
                    break;
                case "Reparaciones por Equipo":
                    reporte = service.obtenerReparacionesPorEquipo(
                            leerFecha(vista.getTxtDesde()),
                            leerFecha(vista.getTxtHasta())
                    );
                    break;
                case "Resumen General":
                    reporte = service.obtenerResumenGeneral();
                    break;
                default:
                    throw new IllegalStateException("Tipo de reporte no reconocido");
            }

            mostrarReporte(reporte);

        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        } catch (DateTimeParseException ex) {
            vista.mostrarError("Formato de fecha inválido. Usá el formato YYYY-MM-DD (ej. 2026-01-31)");
        } catch (SQLException ex) {
            vista.mostrarError("Error de base de datos: " + ex.getMessage());
        }
    }

    private LocalDate leerFecha(javax.swing.JTextField campo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Debe completar ambas fechas para este reporte");
        }
        return LocalDate.parse(texto);
    }

    private void mostrarReporte(Reporte reporte) {
        this.reporteActual = reporte;
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        String[] columnasFormateadas = reporte.getColumnas().stream()
                .map(this::formatearNombreColumna)
                .toArray(String[]::new);
        modelo.setColumnIdentifiers(columnasFormateadas);

        for (Object[] fila : reporte.getFilas()) {
            modelo.addRow(fila);
        }

        vista.ajustarAnchoColumnas();
        vista.getLblTotal().setText("Total: " + reporte.getTotalFilas() + " filas");
    }

    /**
     * Convierte un nombre de columna tipo camelCase (ej. "fechaIngreso")
     * en algo legible para mostrar en la tabla (ej. "Fecha Ingreso").
     * Funciona para cualquier reporte, sin tener que escribir una lista
     * de nombres a mano por cada uno de los 5.
     */
    private String formatearNombreColumna(String columna) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < columna.length(); i++) {
            char c = columna.charAt(i);
            if (i == 0) {
                resultado.append(Character.toUpperCase(c));
            } else if (Character.isUpperCase(c)) {
                resultado.append(' ').append(c);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    private void exportarCSV() {
        if (reporteActual == null) {
            vista.mostrarError("Primero generá un reporte antes de exportar");
            return;
        }

        JFileChooser selector = new JFileChooser();
        selector.setSelectedFile(new java.io.File("reporte.csv"));
        selector.setFileFilter(new FileNameExtensionFilter("Archivo CSV", "csv"));

        int opcion = selector.showSaveDialog(null);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            return; // el usuario canceló
        }

        java.io.File archivo = selector.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".csv")) {
            archivo = new java.io.File(archivo.getParentFile(), archivo.getName() + ".csv");
        }

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(String.join(",", reporteActual.getColumnas()));
            writer.write("\n");

            for (Object[] fila : reporteActual.getFilas()) {
                String[] valores = new String[fila.length];
                for (int i = 0; i < fila.length; i++) {
                    valores[i] = escaparCSV(fila[i]);
                }
                writer.write(String.join(",", valores));
                writer.write("\n");
            }

            vista.mostrarExito("Reporte exportado correctamente a:\n" + archivo.getAbsolutePath());

        } catch (IOException ex) {
            vista.mostrarError("No se pudo exportar el archivo: " + ex.getMessage());
        }
    }

    /**
     * Envuelve el valor en comillas si contiene una coma, para que no rompa
     * el formato CSV (ej. "Pendiente, revisión" no se confunda con 2 columnas).
     */
    private String escaparCSV(Object valor) {
        if (valor == null) return "";
        String texto = valor.toString();
        if (texto.contains(",") || texto.contains("\"")) {
            texto = texto.replace("\"", "\"\"");
            return "\"" + texto + "\"";
        }
        return texto;
    }
}
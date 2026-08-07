package reportes.modelo;

import java.util.List;

/**
 * Modelo genérico para cualquier reporte: guarda los nombres de columnas
 * y las filas de datos, sin importar si es inventario, mantenimientos, etc.
 * Así evitamos crear una clase distinta por cada tipo de reporte.
 */
public class Reporte {
    private final List<String> columnas;
    private final List<Object[]> filas;

    public Reporte(List<String> columnas, List<Object[]> filas) {
        this.columnas = columnas;
        this.filas = filas;
    }

    public List<String> getColumnas() {
        return columnas;
    }

    public List<Object[]> getFilas() {
        return filas;
    }

    public int getTotalFilas() {
        return filas.size();
    }
}
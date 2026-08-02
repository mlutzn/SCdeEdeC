package util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

public class IconUtil {

    // Constructor privado: es una clase de solo métodos estáticos, no se instancia
    private IconUtil() {
    }
    public static ImageIcon cargarIcono(String ruta, int ancho, int alto) {
        URL url = IconUtil.class.getResource(ruta);
        if (url == null) {
            System.err.println("⚠️ No se encontró el ícono: " + ruta);
            return null;
        }
        ImageIcon icono = new ImageIcon(url);
        Image imgEscalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imgEscalada);
    }
}
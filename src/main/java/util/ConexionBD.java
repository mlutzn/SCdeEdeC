package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://195.35.59.3:3306/u484426513_disenocompc226?useSSL=false&serverTimezone=UTC";
    private static final String USER = "u484426513_disenocompc226";
    private static final String PASSWORD = "S2u4uo#d;70+";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexión a la base de datos establecida.");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Error: Driver MySQL no encontrado.");
                e.printStackTrace();
                throw new SQLException("Driver no encontrado");
            } catch (SQLException e) {
                System.err.println("❌ Error al conectar a la base de datos.");
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }

    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("🔒 Conexión cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean testConexion() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Crea las tablas del sistema si no existen todavía.
     * Usa IF NOT EXISTS: si Usuario/Equipo ya están creadas, no las toca.
     * Llamar una sola vez al arrancar la app (después de testConexion()).
     */
    public static boolean inicializarTablas() {
        String sqlEquipo =
                "CREATE TABLE IF NOT EXISTS SCdeEdeC_Equipo (" +
                        "  idEquipo INT AUTO_INCREMENT PRIMARY KEY," +
                        "  tipo VARCHAR(50) NOT NULL," +
                        "  marca VARCHAR(50)," +
                        "  modelo VARCHAR(50)," +
                        "  numeroSerie VARCHAR(100) UNIQUE," +
                        "  fechaAdquisicion DATE," +
                        "  estado VARCHAR(30)," +
                        "  ubicacion VARCHAR(100)" +
                        ")";

        String sqlUsuario =
                "CREATE TABLE IF NOT EXISTS SCdeEdeC_Usuario (" +
                        "  idUsuario INT AUTO_INCREMENT PRIMARY KEY," +
                        "  nombre VARCHAR(100) NOT NULL," +
                        "  apellido VARCHAR(100) NOT NULL," +
                        "  email VARCHAR(150) NOT NULL," +
                        "  telefono VARCHAR(30)," +
                        "  idEquipo INT NULL," +
                        "  CONSTRAINT fk_usuario_equipo FOREIGN KEY (idEquipo) REFERENCES SCdeEdeC_Equipo(idEquipo)" +
                        ")";

        String sqlMantenimiento =
                "CREATE TABLE IF NOT EXISTS SCdeEdeC_Mantenimiento (" +
                        "  idMantenimiento INT AUTO_INCREMENT PRIMARY KEY," +
                        "  idEquipo INT NOT NULL," +
                        "  descripcion VARCHAR(255) NOT NULL," +
                        "  fecha DATE NOT NULL," +
                        "  tipo VARCHAR(20) NOT NULL," +
                        "  tecnico VARCHAR(100) NOT NULL," +
                        "  observaciones TEXT," +
                        "  fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "  CONSTRAINT fk_mantenimiento_equipo FOREIGN KEY (idEquipo) REFERENCES SCdeEdeC_Equipo(idEquipo) ON DELETE CASCADE" +
                        ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Orden importa: Equipo primero (referenciada por FK), después Usuario y Mantenimiento
            stmt.execute(sqlEquipo);
            stmt.execute(sqlUsuario);
            stmt.execute(sqlMantenimiento);

            System.out.println("✅ Tablas verificadas/creadas correctamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Error al crear tablas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
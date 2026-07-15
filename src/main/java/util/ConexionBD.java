package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_equipos?useSSL=false&serverTimezone=America/Costa_Rica";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Cambia por tu contraseña

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
}
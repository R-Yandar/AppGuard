package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/appguard_db";

    private static final String USUARIO =
            System.getenv("APPGUARD_DB_USER");

    private static final String CONTRASENA =
            System.getenv("APPGUARD_DB_PASSWORD");

    public static Connection conectar() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            if (USUARIO == null || CONTRASENA == null) {

                System.out.println(
                        "Error: configure las variables APPGUARD_DB_USER y APPGUARD_DB_PASSWORD."
                );

                return null;
            }

            Connection conexion =
                    DriverManager.getConnection(
                            URL,
                            USUARIO,
                            CONTRASENA
                    );

            System.out.println(
                    "Conexión exitosa a appguard_db"
            );

            return conexion;

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println(
                    "Error de conexión: " + e.getMessage()
            );

            return null;
        }
    }
}
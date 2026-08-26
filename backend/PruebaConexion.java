package backend;

import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
            System.out.println("AppGuard conectado correctamente con MySQL.");
        } else {
            System.out.println("No fue posible conectar AppGuard con MySQL.");
        }
    }
}
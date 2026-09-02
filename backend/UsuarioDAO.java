package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // =========================================
    // INSERTAR USUARIO
    // =========================================
    public boolean insertar(Usuario usuario) {

        String sql = """
        INSERT INTO usuarios
        (id_rol, nombre, apellido, documento, correo, telefono, estado)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, usuario.getIdRol());
sentencia.setString(2, usuario.getNombre());
sentencia.setString(3, usuario.getApellido());
sentencia.setString(4, usuario.getDocumento());
sentencia.setString(5, usuario.getCorreo());
sentencia.setString(6, usuario.getTelefono());
sentencia.setString(7, usuario.getEstado());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al insertar usuario: " + e.getMessage()
            );

            return false;
        }
    }


    // =========================================
    // CONSULTAR USUARIOS
    // =========================================
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT
                    id_usuario,
                    id_rol,
                    nombre,
                    apellido,
                    documento,
                    correo,
                    contrasena,
                    telefono,
                    estado
                FROM usuarios
                ORDER BY id_usuario
                """;

        try (
            Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(
                        resultado.getInt("id_usuario")
                );

                usuario.setIdRol(
                        resultado.getInt("id_rol")
                );

                usuario.setNombre(
                        resultado.getString("nombre")
                );

                usuario.setApellido(
                        resultado.getString("apellido")
                );

                usuario.setDocumento(
                        resultado.getString("documento")
                );

                usuario.setCorreo(
                        resultado.getString("correo")
                );

                usuario.setContrasena(
                        resultado.getString("contrasena")
                );

                usuario.setTelefono(
                        resultado.getString("telefono")
                );

                usuario.setEstado(
                        resultado.getString("estado")
                );

                usuarios.add(usuario);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al consultar usuarios: " + e.getMessage()
            );
        }

        return usuarios;
    }


    // =========================================
    // ACTUALIZAR USUARIO
    // =========================================
    public boolean actualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET
                    id_rol = ?,
                    nombre = ?,
                    apellido = ?,
                    documento = ?,
                    correo = ?,
                    telefono = ?,
                    estado = ?
                WHERE id_usuario = ?
                """;

        try (
            Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, usuario.getIdRol());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getApellido());
            sentencia.setString(4, usuario.getDocumento());
            sentencia.setString(5, usuario.getCorreo());
            sentencia.setString(6, usuario.getTelefono());
            sentencia.setString(7, usuario.getEstado());
            sentencia.setInt(8, usuario.getIdUsuario());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario: " + e.getMessage()
            );

            return false;
        }
    }


    // =========================================
    // ELIMINAR USUARIO
    // =========================================
    public boolean eliminar(int idUsuario) {

        String sql = """
                DELETE FROM usuarios
                WHERE id_usuario = ?
                """;

        try (
            Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(1, idUsuario);

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar usuario: " + e.getMessage()
            );

            return false;
        }
    }
}
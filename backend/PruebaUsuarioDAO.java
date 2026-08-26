package backend;

import java.util.List;

public class PruebaUsuarioDAO {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        // =====================================
        // 1. INSERTAR
        // =====================================

        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setIdRol(3);
        nuevoUsuario.setNombre("Carlos");
        nuevoUsuario.setApellido("Perez");
        nuevoUsuario.setDocumento("1000000002");
        nuevoUsuario.setCorreo("carlos@appguard.com");
        nuevoUsuario.setContrasena("Prueba123");
        nuevoUsuario.setTelefono("3005556677");
        nuevoUsuario.setEstado("Activo");

        boolean insertado = dao.insertar(nuevoUsuario);

        System.out.println(
                insertado
                ? "Usuario insertado correctamente."
                : "No fue posible insertar el usuario."
        );


        // =====================================
        // 2. CONSULTAR
        // =====================================

        System.out.println("\n--- USUARIOS REGISTRADOS ---");

        List<Usuario> usuarios = dao.listar();

        for (Usuario usuario : usuarios) {

            System.out.println(
                    usuario.getIdUsuario()
                    + " | "
                    + usuario.getNombre()
                    + " "
                    + usuario.getApellido()
                    + " | "
                    + usuario.getCorreo()
                    + " | "
                    + usuario.getEstado()
            );
        }


        // =====================================
        // 3. ACTUALIZAR
        // =====================================

        Usuario usuarioActualizar = null;

        for (Usuario usuario : usuarios) {

            if (usuario.getDocumento().equals("1000000002")) {

                usuarioActualizar = usuario;

                break;
            }
        }

        if (usuarioActualizar != null) {

            usuarioActualizar.setTelefono("3112223344");

            usuarioActualizar.setEstado("Inactivo");

            boolean actualizado =
                    dao.actualizar(usuarioActualizar);

            System.out.println(
                    actualizado
                    ? "\nUsuario actualizado correctamente."
                    : "\nNo fue posible actualizar el usuario."
            );
        }


        // =====================================
        // 4. ELIMINAR
        // =====================================

        List<Usuario> usuariosActualizados =
                dao.listar();

        for (Usuario usuario : usuariosActualizados) {

            if (usuario.getDocumento().equals("1000000002")) {

                boolean eliminado =
                        dao.eliminar(
                                usuario.getIdUsuario()
                        );

                System.out.println(
                        eliminado
                        ? "Usuario eliminado correctamente."
                        : "No fue posible eliminar el usuario."
                );

                break;
            }
        }


        // =====================================
        // CONSULTA FINAL
        // =====================================

        System.out.println("\n--- CONSULTA FINAL ---");

        List<Usuario> resultadoFinal =
                dao.listar();

        for (Usuario usuario : resultadoFinal) {

            System.out.println(
                    usuario.getIdUsuario()
                    + " | "
                    + usuario.getNombre()
                    + " "
                    + usuario.getApellido()
                    + " | "
                    + usuario.getCorreo()
                    + " | "
                    + usuario.getEstado()
            );
        }
    }
}

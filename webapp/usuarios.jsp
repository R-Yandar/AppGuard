<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="backend.Usuario" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios - AppGuard</title>
</head>
<body>

    <h1>Listado de usuarios</h1>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Rol</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Documento</th>
            <th>Correo</th>
            <th>Teléfono</th>
            <th>Estado</th>
        </tr>

        <%
            List<Usuario> usuarios =
                    (List<Usuario>) request.getAttribute("usuarios");

            if (usuarios != null) {
                for (Usuario usuario : usuarios) {
        %>

        <tr>
            <td><%= usuario.getIdUsuario() %></td>
            <td><%= usuario.getIdRol() %></td>
            <td><%= usuario.getNombre() %></td>
            <td><%= usuario.getApellido() %></td>
            <td><%= usuario.getDocumento() %></td>
            <td><%= usuario.getCorreo() %></td>
            <td><%= usuario.getTelefono() %></td>
            <td><%= usuario.getEstado() %></td>
        </tr>

        <%
                }
            }
        %>

    </table>

</body>
</html>
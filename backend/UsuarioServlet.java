package backend;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    request.setAttribute("usuarios", usuarioDAO.listar());

    request.getRequestDispatcher("usuarios.jsp").forward(request, response);
}

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    String nombre = request.getParameter("nombre");
    String apellido = request.getParameter("apellido");
    String documento = request.getParameter("documento");
    String correo = request.getParameter("correo");
    String telefono = request.getParameter("telefono");
    String rol = request.getParameter("rol");
int idRol = Integer.parseInt(rol);

Usuario usuario = new Usuario();

usuario.setIdRol(idRol);
usuario.setNombre(nombre);
usuario.setApellido(apellido);
usuario.setDocumento(documento);
usuario.setCorreo(correo);
usuario.setTelefono(telefono);
usuario.setEstado("Activo");

UsuarioDAO usuarioDAO = new UsuarioDAO();

boolean insertado = usuarioDAO.insertar(usuario);

   PrintWriter out = response.getWriter();

if (insertado) {
    out.println("<h1>Usuario registrado correctamente</h1>");
    out.println("<p>El usuario fue guardado en la base de datos de AppGuard.</p>");
} else {
    out.println("<h1>Error al registrar usuario</h1>");
    out.println("<p>No fue posible guardar el usuario en la base de datos.</p>");
}

}

}

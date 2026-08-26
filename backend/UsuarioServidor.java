package backend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioServidor {

    private static final UsuarioDAO dao = new UsuarioDAO();

    public static void main(String[] args) throws IOException {

        HttpServer servidor =
                HttpServer.create(new InetSocketAddress(8080), 0);

        servidor.createContext("/usuarios", UsuarioServidor::manejarUsuarios);

        servidor.setExecutor(null);

        servidor.start();

        System.out.println(
                "API AppGuard ejecutándose en http://localhost:8080"
        );
    }


    private static void manejarUsuarios(HttpExchange exchange)
            throws IOException {

        agregarCors(exchange);

        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        switch (exchange.getRequestMethod()) {

            case "GET" ->
                    listarUsuarios(exchange);

            case "POST" ->
                    crearUsuario(exchange);

            case "PUT" ->
                    actualizarUsuario(exchange);

            case "DELETE" ->
                    eliminarUsuario(exchange);

            default ->
                    responder(exchange, 405, "Método no permitido");
        }
    }


    /* ===============================
       CONSULTAR
       =============================== */

    private static void listarUsuarios(HttpExchange exchange)
            throws IOException {

        List<Usuario> usuarios = dao.listar();

        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < usuarios.size(); i++) {

            Usuario u = usuarios.get(i);

            json.append("{")
                    .append("\"idUsuario\":").append(u.getIdUsuario()).append(",")
                    .append("\"idRol\":").append(u.getIdRol()).append(",")
                    .append("\"nombre\":\"").append(escapar(u.getNombre())).append("\",")
                    .append("\"apellido\":\"").append(escapar(u.getApellido())).append("\",")
                    .append("\"documento\":\"").append(escapar(u.getDocumento())).append("\",")
                    .append("\"correo\":\"").append(escapar(u.getCorreo())).append("\",")
                    .append("\"telefono\":\"").append(escapar(u.getTelefono())).append("\",")
                    .append("\"estado\":\"").append(escapar(u.getEstado())).append("\"")
                    .append("}");

            if (i < usuarios.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        responderJson(exchange, 200, json.toString());
    }


    /* ===============================
       INSERTAR
       =============================== */

    private static void crearUsuario(HttpExchange exchange)
            throws IOException {

        Map<String, String> datos = leerFormulario(exchange);

        Usuario usuario = new Usuario();

        usuario.setIdRol(
                Integer.parseInt(datos.get("idRol"))
        );

        usuario.setNombre(datos.get("nombre"));
        usuario.setApellido(datos.get("apellido"));
        usuario.setDocumento(datos.get("documento"));
        usuario.setCorreo(datos.get("correo"));
        usuario.setContrasena(datos.get("contrasena"));
        usuario.setTelefono(datos.get("telefono"));
        usuario.setEstado(datos.getOrDefault("estado", "Activo"));

        boolean resultado = dao.insertar(usuario);

        if (resultado) {
            responderJson(
                    exchange,
                    201,
                    "{\"mensaje\":\"Usuario creado correctamente\"}"
            );
        } else {
            responderJson(
                    exchange,
                    500,
                    "{\"mensaje\":\"No fue posible crear el usuario\"}"
            );
        }
    }


    /* ===============================
       ACTUALIZAR
       =============================== */

    private static void actualizarUsuario(HttpExchange exchange)
            throws IOException {

        Map<String, String> datos = leerFormulario(exchange);

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(
                Integer.parseInt(datos.get("idUsuario"))
        );

        usuario.setIdRol(
                Integer.parseInt(datos.get("idRol"))
        );

        usuario.setNombre(datos.get("nombre"));
        usuario.setApellido(datos.get("apellido"));
        usuario.setDocumento(datos.get("documento"));
        usuario.setCorreo(datos.get("correo"));
        usuario.setContrasena(datos.get("contrasena"));
        usuario.setTelefono(datos.get("telefono"));
        usuario.setEstado(datos.get("estado"));

        boolean resultado = dao.actualizar(usuario);

        if (resultado) {
            responderJson(
                    exchange,
                    200,
                    "{\"mensaje\":\"Usuario actualizado correctamente\"}"
            );
        } else {
            responderJson(
                    exchange,
                    500,
                    "{\"mensaje\":\"No fue posible actualizar el usuario\"}"
            );
        }
    }


    /* ===============================
       ELIMINAR
       =============================== */

    private static void eliminarUsuario(HttpExchange exchange)
            throws IOException {

        Map<String, String> datos = leerFormulario(exchange);

        int idUsuario =
                Integer.parseInt(datos.get("idUsuario"));

        boolean resultado =
                dao.eliminar(idUsuario);

        if (resultado) {
            responderJson(
                    exchange,
                    200,
                    "{\"mensaje\":\"Usuario eliminado correctamente\"}"
            );
        } else {
            responderJson(
                    exchange,
                    500,
                    "{\"mensaje\":\"No fue posible eliminar el usuario\"}"
            );
        }
    }


    /* ===============================
       UTILIDADES
       =============================== */

    private static Map<String, String> leerFormulario(
            HttpExchange exchange
    ) throws IOException {

        String cuerpo =
                new String(
                        exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8
                );

        Map<String, String> datos = new HashMap<>();

        if (cuerpo.isEmpty()) {
            return datos;
        }

        for (String parametro : cuerpo.split("&")) {

            String[] partes =
                    parametro.split("=", 2);

            if (partes.length == 2) {

                datos.put(
                        URLDecoder.decode(
                                partes[0],
                                StandardCharsets.UTF_8
                        ),
                        URLDecoder.decode(
                                partes[1],
                                StandardCharsets.UTF_8
                        )
                );
            }
        }

        return datos;
    }


    private static void agregarCors(HttpExchange exchange) {

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Origin",
                "*"
        );

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );

        exchange.getResponseHeaders().add(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
    }


    private static void responderJson(
            HttpExchange exchange,
            int codigo,
            String respuesta
    ) throws IOException {

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=UTF-8"
        );

        responder(exchange, codigo, respuesta);
    }


    private static void responder(
            HttpExchange exchange,
            int codigo,
            String respuesta
    ) throws IOException {

        byte[] bytes =
                respuesta.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                codigo,
                bytes.length
        );

        try (
            OutputStream salida =
                    exchange.getResponseBody()
        ) {

            salida.write(bytes);
        }
    }


    private static String escapar(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
package backend;

public class Usuario {

    private int idUsuario;
    private int idRol;
    private String nombre;
    private String apellido;
    private String documento;
    private String correo;
    private String contrasena;
    private String telefono;
    private String estado;

    public Usuario() {
    }

    public Usuario(
            int idUsuario,
            int idRol,
            String nombre,
            String apellido,
            String documento,
            String correo,
            String contrasena,
            String telefono,
            String estado
    ) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
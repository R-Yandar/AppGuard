import UsuarioItem from "./UsuarioItem";

// Lista de usuarios registrados en AppGuard
function ListaUsuarios({
  usuarios,
  onEditar,
  onCambiarEstado,
  onEliminar,
}) {

  return (
    <div className="tabla-usuarios-contenedor">
      <table className="tabla-usuarios">
        <thead>
          <tr>
            <th>Usuario</th>
            <th>Documento</th>
            <th>Correo</th>
            <th>Rol</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>
          {usuarios.map((usuario) => (
          <UsuarioItem
  key={usuario.idUsuario}
  usuario={usuario}
  onEditar={onEditar}
  onCambiarEstado={onCambiarEstado}
  onEliminar={onEliminar}
/>

          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ListaUsuarios;
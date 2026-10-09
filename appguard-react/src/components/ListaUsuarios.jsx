import UsuarioItem from "./UsuarioItem";

/**
 * ListaUsuarios
 *
 * Muestra en una tabla los usuarios registrados en AppGuard
 * y delega las acciones de cada registro al componente UsuarioItem.
 *
 * Funcionalidad/HU:
 * - Consultar usuarios registrados.
 * - Modificar usuarios.
 * - Cambiar el estado de un usuario.
 * - Eliminar usuarios.
 *
 * Props:
 * - usuarios: arreglo con los usuarios que se mostrarán en la tabla.
 * - onEditar: función que solicita editar un usuario.
 * - onCambiarEstado: función que solicita activar o inactivar un usuario.
 * - onEliminar: función que solicita eliminar un usuario.
 */

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
            <th>Teléfono</th>
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
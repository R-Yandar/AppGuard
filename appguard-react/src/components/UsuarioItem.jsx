// Componente que representa cada usuario de la tabla
function UsuarioItem({
  usuario,
  onEditar,
  onCambiarEstado,
  onEliminar,
}) {

  const roles = {
    1: "Administrador",
    2: "Supervisor",
    3: "Guarda",
    4: "Cliente",
  };

  const rol = roles[usuario.idRol] || "Sin rol";

  const iniciales =
    `${usuario.nombre?.charAt(0) || ""}${usuario.apellido?.charAt(0) || ""}`
      .toUpperCase();

  return (
    <tr>
      <td>
        <div className="usuario-tabla">
          <div className="mini-avatar">
            {iniciales}
          </div>

          <div>
            <strong>
              {usuario.nombre} {usuario.apellido}
            </strong>

            <small>{usuario.telefono}</small>
          </div>
        </div>
      </td>

      <td>{usuario.documento}</td>

      <td>{usuario.correo}</td>

      <td>
        <span className={`rol ${rol.toLowerCase()}`}>
          {rol}
        </span>
      </td>

      <td>
        <span className={`estado ${usuario.estado.toLowerCase()}`}>
          {usuario.estado}
        </span>
      </td>

      <td>
        <div className="acciones-tabla">
          <button
            type="button"
            className="btn-accion editar"
            title="Editar usuario"
            onClick={() => onEditar(usuario)}
          >
            <i className="bi bi-pencil-square"></i>
          </button>

          <button
            type="button"
            className="btn-accion estado-usuario"
            title="Cambiar estado"
            onClick={() => onCambiarEstado(usuario)}
          >
            <i className="bi bi-person-check"></i>
          </button>

          <button
            type="button"
            className="btn-accion eliminar"
            title="Eliminar usuario"
            onClick={() => onEliminar(usuario)}
          >
            <i className="bi bi-trash3"></i>
          </button>
        </div>
      </td>
    </tr>
  );
}

export default UsuarioItem;
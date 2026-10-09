import { useEffect, useState } from "react";
import BuscadorUsuarios from "./BuscadorUsuarios";
import ListaUsuarios from "./ListaUsuarios";
import FormularioUsuario from "./FormularioUsuario";
import "../GestionUsuarios.css";

import {
  listarUsuarios,
  actualizarUsuario,
  eliminarUsuario as eliminarUsuarioService,
} from "../services/usuarioService";

/**
 * GestionUsuarios
 *
 * Componente principal del módulo Gestión de Usuarios de AppGuard.
 * Coordina la carga, búsqueda, edición, cambio de estado y eliminación
 * de usuarios, y controla la apertura del formulario de registro o edición.
 *
 * Funcionalidad/HU:
 * - Consultar usuarios registrados.
 * - Buscar usuarios.
 * - Registrar usuarios.
 * - Modificar usuarios.
 * - Cambiar el estado de un usuario.
 * - Eliminar usuarios.
 *
 * Props:
 * - Este componente no recibe props actualmente.
 */

function GestionUsuarios() {
  const [busqueda, setBusqueda] = useState("");
  const [usuarios, setUsuarios] = useState([]);

  const [usuarioSeleccionado, setUsuarioSeleccionado] = useState(null);
const [mostrarFormulario, setMostrarFormulario] = useState(false);

  // Cargar usuarios desde la API
  useEffect(() => {
    cargarUsuarios();
  }, []);

  async function cargarUsuarios() {
  try {
    const datos = await listarUsuarios();
    setUsuarios(datos);
  } catch (error) {
    console.error("Error al cargar usuarios:", error);
  }
}

  // Filtrar usuarios según el texto escrito en el buscador
  const usuariosFiltrados = usuarios.filter((usuario) => {
    const texto = busqueda.toLowerCase().trim();

    return (
      usuario.nombre?.toLowerCase().includes(texto) ||
      usuario.apellido?.toLowerCase().includes(texto) ||
      usuario.documento?.toLowerCase().includes(texto) ||
      usuario.correo?.toLowerCase().includes(texto) ||
      usuario.estado?.toLowerCase().includes(texto)
    );
  });

  function manejarEditarUsuario(usuario) {
  setUsuarioSeleccionado(usuario);
  setMostrarFormulario(true);
}

async function manejarUsuarioGuardado() {
  await cargarUsuarios();

  setMostrarFormulario(false);
  setUsuarioSeleccionado(null);
}

function manejarCancelarFormulario() {
  setMostrarFormulario(false);
  setUsuarioSeleccionado(null);
}

async function manejarCambiarEstado(usuario) {
  const nuevoEstado =
    usuario.estado === "Activo" ? "Inactivo" : "Activo";

  try {
    const datos = {
      idUsuario: usuario.idUsuario,
      nombre: usuario.nombre,
      apellido: usuario.apellido,
      documento: usuario.documento,
      correo: usuario.correo,
      telefono: usuario.telefono,
      idRol: usuario.idRol,
      estado: nuevoEstado,
    };

    await actualizarUsuario(datos);

    await cargarUsuarios();
  } catch (error) {
    console.error("Error al cambiar estado:", error);
    alert("Error al cambiar el estado del usuario.");
  }
}

async function manejarEliminarUsuario(usuario) {
  const confirmar = window.confirm(
    `¿Desea eliminar a ${usuario.nombre} ${usuario.apellido}?`
  );

  if (!confirmar) {
    return;
  }

  try {
    await eliminarUsuarioService(usuario.idUsuario);

    await cargarUsuarios();

    alert("Usuario eliminado correctamente.");
  } catch (error) {
    console.error("Error al eliminar usuario:", error);
    alert("Error al eliminar el usuario.");
  }
}

  const usuariosActivos = usuarios.filter(
    (usuario) => usuario.estado === "Activo"
  ).length;

  return (
    <section className="gestion-usuarios">
      <div className="barra-acciones-usuarios">
        <BuscadorUsuarios
          busqueda={busqueda}
          onBuscar={setBusqueda}
        />

    <button
  type="button"
  className="btn-nuevo-usuario"
  onClick={() => {
    setUsuarioSeleccionado(null);
    setMostrarFormulario(true);
  }}
>
  Nuevo usuario
</button>

      </div>

      <div className="resumen-usuarios">
        <div className="tarjeta-resumen-usuario">
          <div>
            <strong>{usuarios.length}</strong>
            <span>Usuarios registrados</span>
          </div>
        </div>

        <div className="tarjeta-resumen-usuario">
          <div>
            <strong>{usuariosActivos}</strong>
            <span>Usuarios activos</span>
          </div>
        </div>

        <div className="tarjeta-resumen-usuario">
          <div>
            <strong>4</strong>
            <span>Roles disponibles</span>
          </div>
        </div>
      </div>

      <div className="panel-listado-usuarios">
        <div className="encabezado-listado-usuarios">
          <div>
            <h2>Usuarios registrados</h2>
            <p>Consulta y administra los usuarios de AppGuard</p>
          </div>
        </div>

        <ListaUsuarios
  usuarios={usuariosFiltrados}
  onEditar={manejarEditarUsuario}
  onCambiarEstado={manejarCambiarEstado}
  onEliminar={manejarEliminarUsuario}
/>
{mostrarFormulario && (
 <FormularioUsuario
  usuario={usuarioSeleccionado}
  onGuardado={manejarUsuarioGuardado}
  onCancelar={manejarCancelarFormulario}
/>
)}
      </div>
    </section>
  );
}

export default GestionUsuarios;
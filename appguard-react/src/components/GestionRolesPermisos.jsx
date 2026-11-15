import { useEffect, useState } from "react";
import "../PersonalEnServicio.css";

function GestionRolesPermisos() {
  const [roles, setRoles] = useState([]);
  const [permisosPorRol, setPermisosPorRol] = useState({});

  useEffect(() => {
    cargarRoles();
  }, []);

  async function cargarRoles() {
    try {
      const respuesta = await fetch(
        "http://localhost:8080/api/roles"
      );

      if (!respuesta.ok) {
        throw new Error("No fue posible consultar los roles.");
      }

      const datos = await respuesta.json();

      setRoles(datos);

      for (const rol of datos) {
        await cargarPermisosRol(rol.idRol);
      }
    } catch (error) {
      console.error("Error al consultar roles:", error);
    }
  }

  async function cargarPermisosRol(idRol) {
    try {
      const respuesta = await fetch(
        `http://localhost:8080/api/permisos/rol/${idRol}`
      );

      if (!respuesta.ok) {
        throw new Error("No fue posible consultar los permisos.");
      }

      const datos = await respuesta.json();

      setPermisosPorRol((estadoAnterior) => ({
        ...estadoAnterior,
        [idRol]: datos,
      }));
    } catch (error) {
      console.error(
        `Error al consultar permisos del rol ${idRol}:`,
        error
      );
    }
  }

  async function cambiarPermiso(idRol, idPermiso) {
    try {
      const respuesta = await fetch(
        `http://localhost:8080/api/permisos/${idPermiso}/cambiar`,
        {
          method: "PUT",
        }
      );

      if (!respuesta.ok) {
        throw new Error("No fue posible cambiar el permiso.");
      }

      await cargarPermisosRol(idRol);
    } catch (error) {
      console.error("Error al cambiar permiso:", error);
      alert("No fue posible actualizar el permiso.");
    }
  }

  return (
    <section className="personal-servicio">

      <div className="encabezado-personal-servicio">
        <div>
          <h2>Roles y permisos</h2>
          <p>
            Administra los accesos disponibles para cada tipo de usuario.
          </p>
        </div>
      </div>

      <div className="lista-roles-permisos">

        {roles.map((rol) => (
          <div
            key={rol.idRol}
            className="tarjeta-rol-permisos"
          >

            <div className="rol-informacion">
              <h3>{rol.nombreRol}</h3>
              <p>{rol.descripcion}</p>
            </div>

            <div className="permisos-rol">

              {(permisosPorRol[rol.idRol] || []).map((permiso) => (

                <button
                  key={permiso.idPermiso}
                  type="button"
                  className={
                    permiso.permitido
                      ? "permiso-activo"
                      : "permiso-inactivo"
                  }
                  onClick={() =>
                    cambiarPermiso(
                      rol.idRol,
                      permiso.idPermiso
                    )
                  }
                >
                  {permiso.modulo}

                  <span>
                    {permiso.permitido
                      ? "Permitido"
                      : "Bloqueado"}
                  </span>
                </button>

              ))}

            </div>

          </div>
        ))}

      </div>

    </section>
  );
}

export default GestionRolesPermisos;
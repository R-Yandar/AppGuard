import { useState } from "react";
import GestionUsuarios from "./GestionUsuarios";
import GestionRolesPermisos from "./GestionRolesPermisos";
import AjustesOperativos from "./AjustesOperativos";
import "../PersonalEnServicio.css";

function GestionAdministrador() {
  const [opcionActiva, setOpcionActiva] = useState("");

  function cerrarModulo() {
    setOpcionActiva("");
  }

  return (
    <section className="personal-servicio">

      <div className="encabezado-personal-servicio">
        <div>
          <h2>Administración del sistema</h2>
          <p>
            Gestiona usuarios, permisos y configuraciones operativas de AppGuard.
          </p>
        </div>
      </div>

      {!opcionActiva && (
        <div className="opciones-administrador">

          <button
            type="button"
            className="tarjeta-administrador"
            onClick={() => setOpcionActiva("usuarios")}
          >
            <strong>Gestionar usuarios</strong>
            <span>
              Crear, editar, cambiar estado y administrar usuarios.
            </span>
          </button>

          <button
            type="button"
            className="tarjeta-administrador"
            onClick={() => setOpcionActiva("permisos")}
          >
            <strong>Roles y permisos</strong>
            <span>
              Configurar los accesos disponibles para cada rol.
            </span>
          </button>

          <button
            type="button"
            className="tarjeta-administrador"
            onClick={() => setOpcionActiva("ajustes")}
          >
            <strong>Ajustes operativos</strong>
            <span>
              Gestionar cambios excepcionales de personal, puestos y horarios.
            </span>
          </button>

        </div>
      )}

      {opcionActiva === "usuarios" && (
        <div>
          <button
            type="button"
            className="btn-volver-administrador"
            onClick={cerrarModulo}
          >
            ← Volver
          </button>

          <GestionUsuarios />
        </div>
      )}

      {opcionActiva === "permisos" && (
        <div>
          <button
            type="button"
            className="btn-volver-administrador"
            onClick={cerrarModulo}
          >
            ← Volver
          </button>

          <GestionRolesPermisos />
        </div>
      )}

      {opcionActiva === "ajustes" && (
        <div>
          <button
            type="button"
            className="btn-volver-administrador"
            onClick={cerrarModulo}
          >
            ← Volver
          </button>

          <AjustesOperativos />
        </div>
      )}

    </section>
  );
}

export default GestionAdministrador;
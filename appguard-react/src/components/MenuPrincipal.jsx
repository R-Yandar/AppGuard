import { useEffect, useState } from "react";
import { listarPermisosPorRol } from "../services/permisoService";
import "../PersonalEnServicio.css";

function MenuPrincipal({
  idRol = 1,
  onSeleccionarModulo,
}) {
  const [permisos, setPermisos] = useState([]);

  useEffect(() => {
    cargarPermisos();
  }, [idRol]);

  async function cargarPermisos() {
    try {
      const datos = await listarPermisosPorRol(idRol);

      setPermisos(
        datos.filter((permiso) => permiso.permitido === true)
      );
    } catch (error) {
      console.error("Error al consultar permisos:", error);
      setPermisos([]);
    }
  }

  function tienePermiso(modulo) {
    return permisos.some(
      (permiso) => permiso.modulo === modulo
    );
  }

  return (
    <section className="panel-principal-react">

      <div className="bienvenida-panel-react">
        <h1>
          Bienvenido, <span>Brayan Rodríguez</span>
        </h1>

        <p>
          Selecciona el módulo con el que deseas trabajar.
        </p>
      </div>

      <section className="grid-modulos">

        {tienePermiso("Guardas") && (
          <button
            type="button"
            className="tarjeta-modulo tarjeta-guardas"
            onClick={() => onSeleccionarModulo("guardas")}
          >
            <div className="icono-modulo azul">
              <i className="bi bi-person-badge"></i>
            </div>

            <div>
              <h3>Guardas</h3>

              <p>
                Accede a las funciones operativas del personal de vigilancia.
              </p>
            </div>
          </button>
        )}

        {tienePermiso("Supervisión") && (
  <button
    type="button"
    className="tarjeta-modulo tarjeta-supervision"
    onClick={() => onSeleccionarModulo("supervision")}
  >
    <div className="icono-modulo verde">
      <i className="bi bi-people-fill icono-supervision-panel"></i>
    </div>

    <div>
      <h3>Supervisión</h3>

      <p>
        Controla el personal en servicio y la operación.
      </p>
    </div>
  </button>
)}
        {tienePermiso("Botón de pánico") && (
          <button
            type="button"
            className="tarjeta-modulo tarjeta-panico"
            onClick={() => onSeleccionarModulo("panico")}
          >
            <div className="icono-modulo rojo">
              <i className="bi bi-broadcast-pin"></i>
            </div>

            <div>
              <h3>Botón de pánico</h3>

              <p>
                Gestiona alertas de emergencia y su estado.
              </p>
            </div>
          </button>
        )}

        {tienePermiso("Novedades") && (
          <button
            type="button"
            className="tarjeta-modulo tarjeta-novedades"
            onClick={() => onSeleccionarModulo("novedades")}
          >
            <div className="icono-modulo naranja">
              <i className="bi bi-clipboard2-check-fill"></i>
            </div>

            <div>
              <h3>Novedades</h3>

              <p>
                Registra y consulta novedades operativas.
              </p>
            </div>
          </button>
        )}

        {tienePermiso("Reportes") && (
          <button
            type="button"
            className="tarjeta-modulo tarjeta-reportes"
            onClick={() => onSeleccionarModulo("reportes")}
          >
            <div className="icono-modulo morado">
              <i className="bi bi-bar-chart-fill"></i>
            </div>

            <div>
              <h3>Reportes</h3>

              <p>
                Consulta y exporta información operativa.
              </p>
            </div>
          </button>
        )}

        {idRol === 1 && (
          <button
            type="button"
            className="tarjeta-modulo tarjeta-administrador"
            onClick={() => onSeleccionarModulo("administrador")}
          >
            <div className="icono-modulo azul">
              <i className="bi bi-person-gear"></i>
            </div>

            <div>
              <h3>Administrador</h3>

              <p>
                Gestiona usuarios, permisos y ajustes operativos.
              </p>
            </div>
          </button>
        )}

        {tienePermiso("Horarios") && (
          <button
            type="button"
            className="tarjeta-modulo tarjeta-horarios"
            onClick={() => onSeleccionarModulo("horarios")}
          >
            <div className="icono-modulo verde">
              <i className="bi bi-calendar3"></i>
            </div>

            <div>
              <h3>Horarios</h3>

              <p>
                Consulta la programación vigente del personal.
              </p>
            </div>
          </button>
        )}

        {tienePermiso("Clientes") && (
          <button
            type="button"
            className="tarjeta-modulo tarjeta-clientes"
            onClick={() => onSeleccionarModulo("clientes")}
          >
            <div className="icono-modulo naranja">
              <i className="bi bi-people"></i>
            </div>

            <div>
              <h3>Clientes</h3>

              <p>
                Consulta servicios, puestos y personal asignado.
              </p>
            </div>
          </button>
        )}

      </section>

    </section>
  );
}

export default MenuPrincipal;
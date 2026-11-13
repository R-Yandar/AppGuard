import { useEffect, useState } from "react";
import {
  listarAlertas,
  activarAlerta,
  cancelarAlerta,
} from "../services/panicoService";

import "../PersonalEnServicio.css";

function GestionPanico() {
  const [alertas, setAlertas] = useState([]);
  const [alertaActiva, setAlertaActiva] = useState(null);

  useEffect(() => {
    cargarAlertas();
  }, []);

  async function cargarAlertas() {
    try {
      const datos = await listarAlertas();

      setAlertas(datos);

      const activa = datos.find(
        (alerta) => alerta.estado === "Activa"
      );

      setAlertaActiva(activa || null);
    } catch (error) {
      console.error("Error al consultar alertas:", error);
    }
  }

  async function manejarActivar() {
    try {
      await activarAlerta();

      alert("Alerta de pánico activada.");

      await cargarAlertas();
    } catch (error) {
      console.error(error);
      alert("No fue posible activar la alerta.");
    }
  }

  async function manejarCancelar() {
    if (!alertaActiva) {
      return;
    }

    try {
      await cancelarAlerta(alertaActiva.idAlerta);

      alert("Alerta cancelada.");

      await cargarAlertas();
    } catch (error) {
      console.error(error);
      alert("No fue posible cancelar la alerta.");
    }
  }

  return (
    <section className="personal-servicio">

     <div className="encabezado-panico">
  <p>
    Activa una alerta inmediata ante una situación de emergencia
    durante la prestación del servicio.
  </p>
</div>

      <div className="estado-panico">
        <strong>Estado de alerta</strong>

        <p className={alertaActiva ? "panico-activo" : "panico-inactivo"}>
  {alertaActiva
    ? "Alerta activa"
    : "Sin alerta activa"}
</p>
      </div>

      <div className="acciones-panico">
        {!alertaActiva ? (
          <button
            type="button"
            onClick={manejarActivar}
          >
            Activar alerta
          </button>
        ) : (
          <button
            type="button"
            onClick={manejarCancelar}
          >
            Cancelar alerta
          </button>
        )}
      </div>

      <div className="historial-panico">
        <h3>Historial de alertas</h3>

        {alertas.length === 0 ? (
          <p>No hay alertas registradas.</p>
        ) : (
          alertas.map((alerta) => (
            <div
              key={alerta.idAlerta}
              className="alerta-supervision"
            >
              <div>
                <strong>
                  Alerta #{alerta.idAlerta}
                </strong>

                <span>
                  Fecha: {alerta.fecha}
                </span>

                <span>
                  Ubicación:{" "}
                  {alerta.ubicacion || "No registrada"}
                </span>

                <span>
                  Estado: {alerta.estado}
                </span>
              </div>
            </div>
          ))
        )}
      </div>
    </section>
  );
}

export default GestionPanico;
import { useEffect, useState } from "react";

import {
  listarAlertasActivas,
  atenderAlerta,
} from "../services/alertaService";

import "../PersonalEnServicio.css";

function AlertasSupervision({ onCerrar }) {
  const [alertas, setAlertas] = useState([]);

  useEffect(() => {
    cargarAlertas();
  }, []);

  async function cargarAlertas() {
    try {
      const datos = await listarAlertasActivas();
      setAlertas(datos);
    } catch (error) {
      console.error("Error al consultar alertas:", error);
      alert("No fue posible consultar las alertas.");
    }
  }

  async function manejarAtender(idAlerta) {
    try {
      const respuesta = await atenderAlerta(idAlerta);

      alert(respuesta.mensaje);

      await cargarAlertas();
    } catch (error) {
      console.error("Error al atender alerta:", error);
      alert("No fue posible atender la alerta.");
    }
  }

  return (
    <section className="personal-servicio">
      <div className="encabezado-personal-servicio">
        <div>
          <h2>Alertas</h2>
          <p>
            Consulta y atiende las alertas generadas durante la prestación
            del servicio.
          </p>
        </div>

        <button type="button" onClick={onCerrar}>
          Cerrar
        </button>
      </div>

      {alertas.length === 0 ? (
        <div className="sin-alertas">
          No hay alertas activas actualmente.
        </div>
      ) : (
        <div className="lista-alertas">
          {alertas.map((alerta) => (
            <div
              className="alerta-supervision"
              key={alerta.idAlerta}
            >
              <div>
                <strong>
                  Alerta #{alerta.idAlerta}
                </strong>

                <span>
                  Guarda: {alerta.idGuarda}
                </span>

                <span>
                  Fecha: {alerta.fecha}
                </span>

                <span>
                  Ubicación: {alerta.ubicacion || "No registrada"}
                </span>
              </div>

              <div>
                <span>Estado</span>

                <strong className="estado-alerta-activa">
                  {alerta.estado}
                </strong>

                <button
                  type="button"
                  onClick={() =>
                    manejarAtender(alerta.idAlerta)
                  }
                >
                  Marcar atendida
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </section>
  );
}

export default AlertasSupervision;
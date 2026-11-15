import { useState } from "react";
import { buscarGuardas } from "../services/guardaService";
import { listarTurnosPorGuarda } from "../services/horarioService";

import "../PersonalEnServicio.css";

function GestionHorarios() {
  const [textoBusqueda, setTextoBusqueda] = useState("");
  const [resultados, setResultados] = useState([]);
  const [guardaSeleccionado, setGuardaSeleccionado] = useState(null);
  const [turnos, setTurnos] = useState([]);
  const [buscando, setBuscando] = useState(false);

  async function manejarBusqueda() {
    if (!textoBusqueda.trim()) {
      setResultados([]);
      return;
    }

    try {
      setBuscando(true);

      const datos = await buscarGuardas(textoBusqueda);

      setResultados(datos);
    } catch (error) {
      alert(error.message);
    } finally {
      setBuscando(false);
    }
  }

  async function seleccionarGuarda(guarda) {
    try {
      setGuardaSeleccionado(guarda);
      setResultados([]);

      const datos = await listarTurnosPorGuarda(
        guarda.idGuarda
      );

      setTurnos(datos);
    } catch (error) {
      alert(error.message);
    }
  }

  function limpiarBusqueda() {
    setTextoBusqueda("");
    setResultados([]);
    setGuardaSeleccionado(null);
    setTurnos([]);
  }

  function obtenerDia(fecha) {
    if (!fecha) {
      return "";
    }

    const fechaLocal = new Date(
      `${fecha}T00:00:00`
    );

    return fechaLocal.toLocaleDateString(
      "es-CO",
      {
        weekday: "long",
      }
    );
  }

  function formatearFecha(fecha) {
    if (!fecha) {
      return "";
    }

    const [anio, mes, dia] = fecha.split("-");

    return `${dia}/${mes}/${anio}`;
  }

  function formatearHora(hora) {
    if (!hora) {
      return "";
    }

    return hora.substring(0, 5);
  }

  return (
    <section className="personal-servicio">

      <div className="encabezado-personal-servicio">
        <div>
          <h2>Consulta de cronogramas</h2>

          <p>
            Consulta la programación asignada al personal de guardas.
          </p>
        </div>
      </div>

      <div className="buscador-horarios">

        <div className="campo-buscador-horarios">

          <label>
            Buscar guarda
          </label>

          <input
            type="text"
            value={textoBusqueda}
            onChange={(evento) =>
              setTextoBusqueda(evento.target.value)
            }
            onKeyDown={(evento) => {
              if (evento.key === "Enter") {
                manejarBusqueda();
              }
            }}
            placeholder="Nombre, documento, código o ID"
          />

        </div>

        <button
          type="button"
          className="btn-principal"
          onClick={manejarBusqueda}
        >
          {buscando ? "Buscando..." : "Buscar"}
        </button>

        <button
          type="button"
          className="btn-secundario"
          onClick={limpiarBusqueda}
        >
          Limpiar
        </button>

      </div>

      {resultados.length > 0 && (
        <div className="resultados-busqueda-horarios">

          {resultados.map((guarda) => (
            <button
              type="button"
              key={guarda.idGuarda}
              className="resultado-guarda-horarios"
              onClick={() =>
                seleccionarGuarda(guarda)
              }
            >

              <strong>
                {guarda.nombreCompleto}
              </strong>

              <span>
                Documento: {guarda.documento}
              </span>

              <span>
                Código: {guarda.codigoGuarda}
              </span>

              <span>
                Estado: {guarda.estado}
              </span>

            </button>
          ))}

        </div>
      )}

      {guardaSeleccionado && (
        <>
          <div className="resumen-guarda-horarios">

            <div>
              <span>Guarda</span>

              <strong>
                {guardaSeleccionado.nombreCompleto}
              </strong>
            </div>

            <div>
              <span>Documento</span>

              <strong>
                {guardaSeleccionado.documento}
              </strong>
            </div>

            <div>
              <span>Código</span>

              <strong>
                {guardaSeleccionado.codigoGuarda}
              </strong>
            </div>

            <div>
              <span>Estado</span>

              <strong>
                {guardaSeleccionado.estado}
              </strong>
            </div>

          </div>

          <div className="tabla-responsive">

            <table className="tabla-appguard">

              <thead>
                <tr>
                  <th>Fecha</th>
                  <th>Día</th>
                  <th>Puesto</th>
                  <th>Dirección</th>
                  <th>Ciudad</th>
                  <th>Hora inicio</th>
                  <th>Hora fin</th>
                  <th>Estado</th>
                </tr>
              </thead>

              <tbody>

                {turnos.length === 0 ? (
                  <tr>
                    <td colSpan="8">
                      No hay horarios programados para este guarda.
                    </td>
                  </tr>
                ) : (
                  turnos.map((turno) => (
                    <tr key={turno.idTurno}>

                      <td>
                        {formatearFecha(turno.fecha)}
                      </td>

                      <td className="texto-capitalizado">
                        {obtenerDia(turno.fecha)}
                      </td>

                      <td>
                        {turno.puesto}
                      </td>

                      <td>
                        {turno.direccion}
                      </td>

                      <td>
                        {turno.ciudad}
                      </td>

                      <td>
                        {formatearHora(turno.horaInicio)}
                      </td>

                      <td>
                        {formatearHora(turno.horaFin)}
                      </td>

                      <td>
                        <span
                          className={`estado-turno estado-${turno.estado?.toLowerCase()}`}
                        >
                          {turno.estado}
                        </span>
                      </td>

                    </tr>
                  ))
                )}

              </tbody>

            </table>

          </div>
        </>
      )}

    </section>
  );
}

export default GestionHorarios;
import { useState } from "react";
import FormularioAsistencia from "./FormularioAsistencia";
import EstadoTurno from "./EstadoTurno";
import {
  consultarEstadoTurno,
  finalizarTurno,
} from "../services/asistenciaService";
import "../GestionGuardas.css";

/**
 * GestionGuardas
 *
 * Componente principal del módulo Gestión de Guardas de AppGuard.
 *
 * Funcionalidades:
 * - Registrar asistencia.
* - Consultar estado del turno.
* - Finalizar turno.
 */

function GestionGuardas() {
  const [mostrarAsistencia, setMostrarAsistencia] = useState(false);
const [turno, setTurno] = useState(null);

async function manejarEstadoTurno() {
  try {
    const idGuarda = 1;
    const datos = await consultarEstadoTurno(idGuarda);

    setTurno(datos);
    setMostrarAsistencia(false);
  } catch (error) {
    console.error("Error al consultar el turno:", error);
    alert("No fue posible consultar el estado del turno.");
  }
}

async function manejarFinalizarTurno() {
  try {
    const idGuarda = 1;

    const respuesta = await finalizarTurno(idGuarda);

    alert(respuesta.mensaje);

    const datosActualizados =
      await consultarEstadoTurno(idGuarda);

    setTurno(datosActualizados);
    setMostrarAsistencia(false);

  } catch (error) {
    console.error("Error al finalizar el turno:", error);
    alert("No fue posible finalizar el turno.");
  }
}

  return (
    <section className="gestion-guardas">

      <div className="encabezado-guardas">
        <h1>Guardas</h1>
        <p>Gestión operativa del personal de vigilancia</p>
      </div>

      <div className="grid-funciones-guardas">

        <button
          type="button"
          className="tarjeta-guarda"
          onClick={() => setMostrarAsistencia(true)}
        >
          <h3>Registrar asistencia</h3>
          <p>
  Registra automáticamente la fecha y hora de ingreso
  al iniciar el turno.
</p>
        </button>

        <button
          type="button"
          className="tarjeta-guarda"
          onClick={manejarEstadoTurno}
        >
          <h3>Estado del turno</h3>
          <p>
  Consulta el horario, puesto asignado y estado actual
  del turno.
</p>
        </button>

        <button
          type="button"
          className="tarjeta-guarda"
          onClick={manejarFinalizarTurno}
        >
          <h3>Finalizar turno</h3>
          <p>
  Registra la hora de salida y finaliza el turno activo.
</p>
        </button>

      </div>

      {mostrarAsistencia && (
        <FormularioAsistencia
          onCancelar={() => setMostrarAsistencia(false)}
        />
      )}

      {turno && (
  <EstadoTurno
    turno={turno}
    onCerrar={() => setTurno(null)}
  />
)}

    </section>
  );
}

export default GestionGuardas;
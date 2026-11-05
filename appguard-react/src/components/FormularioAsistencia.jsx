import { registrarAsistencia } from "../services/asistenciaService";

/**
 * FormularioAsistencia
 *
 * Registra la asistencia de un guarda utilizando
 * la API REST de AppGuard.
 */

function FormularioAsistencia({ onCancelar }) {

  async function manejarEnvio(evento) {
    evento.preventDefault();

    try {
      // Temporalmente usamos un ID de guarda de prueba.
      // Luego se obtendrá del usuario autenticado.
      const idGuarda = 1;

      const asistencia = await registrarAsistencia(idGuarda);

      alert(
        `Asistencia registrada\nFecha: ${asistencia.fecha}\nHora: ${asistencia.horaEntrada}\nEstado: ${asistencia.estado}`
      );

      if (onCancelar) {
        onCancelar();
      }

    } catch (error) {
      console.error("Error al registrar asistencia:", error);

      alert("No fue posible registrar la asistencia.");
    }
  }

  return (
    <form
      className="formulario-asistencia"
      onSubmit={manejarEnvio}
    >
      <h2>Registrar asistencia</h2>

      <p>
        La fecha, hora y estado de llegada
        se registrarán automáticamente.
      </p>

      <div className="acciones-formulario">
        <button
          type="button"
          onClick={onCancelar}
        >
          Cancelar
        </button>

        <button type="submit">
          Registrar asistencia
        </button>
      </div>
    </form>
  );
}

export default FormularioAsistencia;
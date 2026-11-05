function EstadoTurno({ turno, onCerrar }) {
  if (!turno) {
    return null;
  }

  return (
    <section className="estado-turno">
      <div className="encabezado-estado-turno">

        <div>
          <h2>Estado del turno</h2>
          <p>
            Información actual del servicio del guarda
          </p>
        </div>

        <span className="estado-turno-activo">
          {turno.estadoTurno}
        </span>

      </div>

      <div className="detalle-turno">

        <div className="dato-turno">
          <span>ID del guarda</span>
          <strong>{turno.idGuarda}</strong>
        </div>

        <div className="dato-turno">
          <span>Fecha</span>
          <strong>{turno.fecha}</strong>
        </div>

        <div className="dato-turno">
          <span>Hora de entrada</span>
          <strong>{turno.horaEntrada}</strong>
        </div>

        <div className="dato-turno">
          <span>Asistencia</span>
          <strong>{turno.asistencia}</strong>
        </div>

        <div className="dato-turno">
          <span>Hora de salida</span>
          <strong>{turno.horaSalida}</strong>
        </div>

        <div className="dato-turno">
          <span>Estado del turno</span>
          <strong>{turno.estadoTurno}</strong>
        </div>

      </div>

      <div className="acciones-estado-turno">
        <button
          type="button"
          onClick={onCerrar}
        >
          Cerrar
        </button>
      </div>

    </section>
  );
}

export default EstadoTurno;
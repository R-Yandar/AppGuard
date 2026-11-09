import "../PersonalEnServicio.css";

function ControlAsistencia({ asistencias, onCerrar }) {
  return (
    <section className="personal-servicio">
      <div className="encabezado-personal-servicio">
        <div>
          <h2>Control de asistencia</h2>
          <p>Consulta y verificación de los registros de asistencia.</p>
        </div>

        <button type="button" onClick={onCerrar}>
          Cerrar
        </button>
      </div>

      {asistencias.length === 0 ? (
        <p>No hay registros de asistencia disponibles.</p>
      ) : (
        <div className="tabla-personal-servicio">
          <table>
            <thead>
              <tr>
                <th>ID Guarda</th>
                <th>Fecha</th>
                <th>Hora entrada</th>
                <th>Hora salida</th>
                <th>Estado</th>
              </tr>
            </thead>

            <tbody>
              {asistencias.map((item) => (
                <tr key={item.idAsistencia}>
                  <td>{item.idGuarda}</td>
                  <td>{item.fecha}</td>
                  <td>{item.horaEntrada}</td>
                  <td>{item.horaSalida || "Pendiente"}</td>
                  <td>{item.estado}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}

export default ControlAsistencia;
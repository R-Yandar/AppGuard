import "../PersonalEnServicio.css";

function PersonalEnServicio({ personal, onCerrar }) {
  return (
    <section className="personal-servicio">
      <div className="encabezado-personal-servicio">
        <div>
          <h2>Personal en servicio</h2>
          <p>Personal que actualmente se encuentra prestando servicio.</p>
        </div>

        <button type="button" onClick={onCerrar}>
          Cerrar
        </button>
      </div>

      {personal.length === 0 ? (
        <p>No hay personal en servicio actualmente.</p>
      ) : (
        <div className="tabla-personal-servicio">
          <table>
            <thead>
              <tr>
                <th>ID Guarda</th>
                <th>Fecha</th>
                <th>Hora entrada</th>
                <th>Asistencia</th>
                <th>Estado turno</th>
              </tr>
            </thead>

            <tbody>
              {personal.map((item) => (
                <tr key={item.idAsistencia}>
                  <td>{item.idGuarda}</td>
                  <td>{item.fecha}</td>
                  <td>{item.horaEntrada}</td>
                  <td>{item.asistencia}</td>
                  <td>{item.estadoTurno}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}

export default PersonalEnServicio;
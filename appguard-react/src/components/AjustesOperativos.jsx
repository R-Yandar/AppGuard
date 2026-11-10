import { useEffect, useState } from "react";

import {
  listarGuardasActivos,
  listarPuestosActivos,
} from "../services/ajusteOperativoService";

import "../PersonalEnServicio.css";

function AjustesOperativos({ onCerrar }) {
  const [guardas, setGuardas] = useState([]);
  const [puestos, setPuestos] = useState([]);

  const [idGuarda, setIdGuarda] = useState("");
  const [idPuesto, setIdPuesto] = useState("");

  const [fecha, setFecha] = useState("");
  const [horaInicio, setHoraInicio] = useState("");
  const [horaFin, setHoraFin] = useState("");
  const [estado, setEstado] = useState("");

  const [buscarGuarda, setBuscarGuarda] = useState("");
  const [buscarPuesto, setBuscarPuesto] = useState("");

  const [mostrarResultadosGuardas, setMostrarResultadosGuardas] =
    useState(false);

  const [mostrarResultadosPuestos, setMostrarResultadosPuestos] =
    useState(false);
    const [guardaSeleccionado, setGuardaSeleccionado] = useState(null);


  useEffect(() => {
    cargarDatos();
  }, []);

  async function cargarDatos() {
    try {
      const [guardasData, puestosData] = await Promise.all([
        listarGuardasActivos(),
        listarPuestosActivos(),
      ]);

      setGuardas(guardasData);
      setPuestos(puestosData);
    } catch (error) {
      console.error("Error al cargar ajustes operativos:", error);
      alert("No fue posible cargar los ajustes operativos.");
    }
  }

  function manejarEnvio(evento) {
    evento.preventDefault();

    if (!idGuarda) {
      alert("Selecciona un guarda.");
      return;
    }

    if (!idPuesto) {
      alert("Selecciona un puesto.");
      return;
    }

    if (!fecha) {
      alert("Selecciona la fecha.");
      return;
    }

    if (!horaInicio || !horaFin) {
      alert("Selecciona el horario.");
      return;
    }

    if (!estado) {
      alert("Selecciona el estado.");
      return;
    }

    alert(
      "La información está lista. La conexión con el cronograma se realizará en el siguiente paso."
    );
  }

  return (
    <section className="personal-servicio">
      <div className="encabezado-personal-servicio">
        <div>
          <h2>Ajustes operativos</h2>
          <p>
            Modifica fechas, horarios, puestos o personal según las
            necesidades de la operación.
          </p>
        </div>

        <button type="button" onClick={onCerrar}>
          Cerrar
        </button>
      </div>

      <form onSubmit={manejarEnvio}>
        <div>
          <label>Buscar guarda</label>

          <input
            type="text"
            value={buscarGuarda}
            onChange={(evento) => {
              setBuscarGuarda(evento.target.value);
              setMostrarResultadosGuardas(true);
            }}
            onFocus={() => setMostrarResultadosGuardas(true)}
            placeholder="Nombre, documento o código"
          />

          {mostrarResultadosGuardas &&
            buscarGuarda.trim() !== "" && (
              <div className="resultados-guardas">
                {guardas
                  .filter((guarda) => {
                    const texto =
                      buscarGuarda.toLowerCase();

                    return (
                      guarda.nombre
                        ?.toLowerCase()
                        .includes(texto) ||
                      guarda.apellido
                        ?.toLowerCase()
                        .includes(texto) ||
                      guarda.documento
                        ?.toString()
                        .includes(texto) ||
                      guarda.codigoGuarda
                        ?.toLowerCase()
                        .includes(texto)
                    );
                  })
                  .map((guarda) => (
                    <button
                      type="button"
                      key={guarda.idGuarda}
                      className={
                        Number(idGuarda) ===
                        guarda.idGuarda
                          ? "guarda-resultado seleccionado"
                          : "guarda-resultado"
                      }
                onClick={() => {
  setIdGuarda(guarda.idGuarda);
  setGuardaSeleccionado(guarda);

  setBuscarGuarda(
    `${guarda.nombre} ${guarda.apellido}`
  );

  setMostrarResultadosGuardas(false);
}}

                    >
                      <strong>
                        {guarda.nombre}{" "}
                        {guarda.apellido}
                      </strong>

                      <span>
                        Documento: {guarda.documento}
                      </span>

                      <span>
                        Código: {guarda.codigoGuarda}
                      </span>
                    </button>
                  ))}
              </div>
            )}
        </div>

{guardaSeleccionado && (
  <div className="guarda-seleccionado-info">
    <div>
      <strong>
        {guardaSeleccionado.nombre} {guardaSeleccionado.apellido}
      </strong>

      <span>
        Código: {guardaSeleccionado.codigoGuarda}
      </span>
    </div>

    <div>
      <span>Estado operativo</span>

<strong className="estado-guarda-activo">
  Disponible
</strong>
    </div>
  </div>
)}

        <div>
          <label>Buscar puesto</label>

          <input
            type="text"
            value={buscarPuesto}
            onChange={(evento) => {
              setBuscarPuesto(evento.target.value);
              setMostrarResultadosPuestos(true);
            }}
            onFocus={() =>
              setMostrarResultadosPuestos(true)
            }
            placeholder="Nombre, dirección o ciudad"
          />

          {mostrarResultadosPuestos &&
            buscarPuesto.trim() !== "" && (
              <div className="resultados-puestos">
                {puestos
                  .filter((puesto) => {
                    const texto =
                      buscarPuesto.toLowerCase();

                    return (
                      puesto.nombrePuesto
                        ?.toLowerCase()
                        .includes(texto) ||
                      puesto.direccion
                        ?.toLowerCase()
                        .includes(texto) ||
                      puesto.ciudad
                        ?.toLowerCase()
                        .includes(texto)
                    );
                  })
                  .map((puesto) => (
                    <button
                      type="button"
                      key={puesto.idPuesto}
                      className={
                        Number(idPuesto) ===
                        puesto.idPuesto
                          ? "puesto-resultado seleccionado"
                          : "puesto-resultado"
                      }
                      onClick={() => {
                        setIdPuesto(puesto.idPuesto);

                        setBuscarPuesto(
                          puesto.nombrePuesto
                        );

                        setMostrarResultadosPuestos(
                          false
                        );
                      }}
                    >
                      <strong>
                        {puesto.nombrePuesto}
                      </strong>

                      <span>
                        Dirección: {puesto.direccion}
                      </span>

                      <span>
                        Ciudad: {puesto.ciudad}
                      </span>
                    </button>
                  ))}
              </div>
            )}
        </div>

<div className="fila-horario">

  <div>
    <label>Fecha</label>

    <input
      type="date"
      value={fecha}
      onChange={(evento) =>
        setFecha(evento.target.value)
      }
    />
  </div>

  <div>
    <label>Hora inicio</label>

    <input
      type="time"
      value={horaInicio}
      onChange={(evento) =>
        setHoraInicio(evento.target.value)
      }
    />
  </div>

  <div>
    <label>Hora fin</label>

    <input
      type="time"
      value={horaFin}
      onChange={(evento) =>
        setHoraFin(evento.target.value)
      }
    />
  </div>

</div>
    
        <div>
          <label>Estado</label>

          <select
            value={estado}
            onChange={(evento) =>
              setEstado(evento.target.value)
            }
          >
            <option value="">
              Seleccione estado
            </option>

            <option value="Programado">
              Programado
            </option>

            <option value="Cancelado">
              Cancelado
            </option>

            <option value="Finalizado">
              Finalizado
            </option>
          </select>
        </div>

        <button type="submit">
          Guardar ajuste
        </button>
      </form>
    </section>
  );
}

export default AjustesOperativos;
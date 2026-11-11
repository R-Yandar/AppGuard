import { useState } from "react";
import PersonalEnServicio from "./PersonalEnServicio";
import { listarPersonalEnServicio } from "../services/asistenciaService";
import "../GestionSupervision.css";
import ControlAsistencia from "./ControlAsistencia";
import { listarAsistencias } from "../services/asistenciaService";
import FormularioNovedad from "./FormularioNovedad";
import AjustesOperativos from "./AjustesOperativos";
import AlertasSupervision from "./AlertasSupervision";

function GestionSupervision() {

    const [personal, setPersonal] = useState([]);
const [mostrarPersonal, setMostrarPersonal] = useState(false);
const [asistencias, setAsistencias] = useState([]);
const [mostrarAsistencias, setMostrarAsistencias] = useState(false);
const [mostrarNovedad, setMostrarNovedad] = useState(false);
const [mostrarAjustes, setMostrarAjustes] = useState(false);
const [mostrarAlertas, setMostrarAlertas] = useState(false);

async function manejarPersonalEnServicio() {
  try {
    const datos = await listarPersonalEnServicio();
    setPersonal(datos);
    setMostrarPersonal(true);
  } catch (error) {
    console.error("Error al consultar personal en servicio:", error);
    alert("No fue posible consultar el personal en servicio.");
  }

  }
  
  async function manejarControlAsistencia() {
  try {
    const datos = await listarAsistencias();
    setAsistencias(datos);
    setMostrarAsistencias(true);
  } catch (error) {
    console.error("Error al consultar asistencias:", error);
    alert("No fue posible consultar el control de asistencia.");
  }
}

  return (
    <section className="gestion-supervision">

      <div className="grid-funciones-supervision">

        <button
  className="tarjeta-supervision azul"
  onClick={manejarPersonalEnServicio}
>
          <i className="bi bi-people-fill"></i>
          <h3>Personal en servicio</h3>
          <p>
            Consulta el personal que se encuentra actualmente
            prestando servicio.
          </p>
        </button>

        <button
  className="tarjeta-supervision verde"
  onClick={manejarControlAsistencia}
>
          <i className="bi bi-person-check-fill"></i>
          <h3>Control de asistencia</h3>
          <p>
            Verifica la asistencia y cumplimiento
            del personal operativo.
          </p>
        </button>

        <button
  className="tarjeta-supervision naranja"
  onClick={() => setMostrarNovedad(true)}
>
          <i className="bi bi-clipboard-plus-fill"></i>
          <h3>Reportar novedad</h3>
          <p>
            Registra novedades identificadas durante
            la supervisión del servicio.
          </p>
        </button>

        <button
  className="tarjeta-supervision morado"
  onClick={() => setMostrarAjustes(true)}
>
          <i className="bi bi-sliders"></i>
          <h3>Ajustes operativos</h3>
          <p>
            Realiza cambios de turnos, puestos o personal
            según las necesidades de la operación.
          </p>
        </button>

        <button
  className="tarjeta-supervision rojo"
  onClick={() => setMostrarAlertas(true)}
>
          <i className="bi bi-bell-fill"></i>
          <h3>Alertas</h3>
          <p>
            Consulta y atiende las alertas generadas
            durante la prestación del servicio.
          </p>
        </button>

      </div>

      {mostrarPersonal && (
  <PersonalEnServicio
    personal={personal}
    onCerrar={() => setMostrarPersonal(false)}
  />
)}

{mostrarAsistencias && (
  <ControlAsistencia
    asistencias={asistencias}
    onCerrar={() => setMostrarAsistencias(false)}
  />
)}

{mostrarNovedad && (
  <FormularioNovedad
    onCerrar={() => setMostrarNovedad(false)}
  />
)}

{mostrarAjustes && (
  <AjustesOperativos
    onCerrar={() => setMostrarAjustes(false)}
  />
  
)}

{mostrarAlertas && (
  <AlertasSupervision
    onCerrar={() => setMostrarAlertas(false)}
  />
)}

</section>

);

}

export default GestionSupervision;
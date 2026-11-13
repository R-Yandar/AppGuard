import { useEffect, useState } from "react";
import FormularioNovedad from "./FormularioNovedad";
import {
  listarNovedades,
  listarEvidenciasNovedad,
} from "../services/novedadService";
import "../PersonalEnServicio.css";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

function GestionNovedades() {
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [novedades, setNovedades] = useState([]);
  const [fechaFiltro, setFechaFiltro] = useState("");
const [evidencias, setEvidencias] = useState([]);
const [novedadSeleccionada, setNovedadSeleccionada] = useState(null);

  useEffect(() => {
    cargarNovedades();
  }, []);

  async function cargarNovedades() {
    try {
      const datos = await listarNovedades();
      setNovedades(datos);
    } catch (error) {
      console.error("Error al consultar novedades:", error);
    }
  }

  function cerrarFormulario() {
    setMostrarFormulario(false);
    cargarNovedades();
  }

  const novedadesFiltradas = fechaFiltro
    ? novedades.filter((novedad) => novedad.fecha === fechaFiltro)
    : novedades;

async function verEvidencias(novedad) {
  try {
    const datos = await listarEvidenciasNovedad(
      novedad.idNovedad
    );

    setEvidencias(datos);
    setNovedadSeleccionada(novedad);
  } catch (error) {
    console.error("Error al consultar evidencias:", error);
    alert("No fue posible consultar las evidencias.");
  }
}

function exportarPDF() {
  if (novedadesFiltradas.length === 0) {
    alert("No hay novedades para exportar.");
    return;
  }

  const documento = new jsPDF();

  documento.setFontSize(18);
  documento.text("AppGuard - Reporte de novedades", 14, 18);

  documento.setFontSize(10);

  if (fechaFiltro) {
    documento.text(
      `Fecha consultada: ${fechaFiltro}`,
      14,
      27
    );
  } else {
    documento.text(
      "Historial general de novedades",
      14,
      27
    );
  }

  const filas = novedadesFiltradas.map((novedad) => [
    novedad.idNovedad,
    novedad.tipo || "",
    novedad.descripcion || "",
    novedad.fecha || "",
    novedad.hora || "",
  ]);

  autoTable(documento, {
    startY: 34,

    head: [[
      "#",
      "Tipo de novedad",
      "Descripción",
      "Fecha",
      "Hora",
    ]],

    body: filas,

    styles: {
      fontSize: 9,
      cellPadding: 3,
    },

    headStyles: {
      fontStyle: "bold",
    },

    columnStyles: {
      0: { cellWidth: 12 },
      1: { cellWidth: 32 },
      2: { cellWidth: 80 },
      3: { cellWidth: 30 },
      4: { cellWidth: 25 },
    },
  });

  const nombreArchivo = fechaFiltro
    ? `novedades_${fechaFiltro}.pdf`
    : "historial_novedades.pdf";

  documento.save(nombreArchivo);
}

  return (
    <section className="personal-servicio novedades-modulo">

      <div className="encabezado-novedades">
        <div>
          <h2>Registrar y consultar novedades</h2>
          <p>
            Consulta el historial, filtra por fecha y registra nuevas novedades.
          </p>
        </div>

        <button
          type="button"
          className="btn-registrar-novedad"
          onClick={() => setMostrarFormulario(true)}
        >
          + Registrar novedad
        </button>
      </div>

      {mostrarFormulario && (
        <FormularioNovedad
          onCerrar={cerrarFormulario}
        />
      )}

      <div className="seccion-historial-novedades">

        <div className="titulo-historial-novedades">
          <h3>Historial de novedades</h3>
        </div>

        <div className="barra-filtros-novedades">

          <div className="campo-filtro-fecha">
            <label htmlFor="fechaFiltro">
              Buscar por fecha
            </label>

            <input
              id="fechaFiltro"
              type="date"
              value={fechaFiltro}
              onChange={(e) => setFechaFiltro(e.target.value)}
            />
          </div>

          {fechaFiltro && (
            <button
              type="button"
              className="btn-limpiar-filtro"
              onClick={() => setFechaFiltro("")}
            >
              Limpiar
            </button>
          )}

          <button
  type="button"
  className="btn-exportar-pdf"
  onClick={exportarPDF}
>
  Exportar PDF
</button>

        </div>

        {novedades.length === 0 ? (
          <div className="sin-alertas">
            No hay novedades registradas.
          </div>
        ) : novedadesFiltradas.length === 0 ? (
          <div className="sin-alertas">
            No hay novedades registradas para la fecha seleccionada.
          </div>
        ) : (
          <div className="tabla-novedades-contenedor">

            <table className="tabla-novedades">

              <thead>
                <tr>
                  <th>#</th>
                  <th>Tipo de novedad</th>
                  <th>Descripción</th>
                  <th>Fecha</th>
                  <th>Hora</th>
                  <th>Evidencias</th>
                </tr>
              </thead>

              <tbody>
                {novedadesFiltradas.map((novedad) => (
                  <tr key={novedad.idNovedad}>

                    <td>
                      {novedad.idNovedad}
                    </td>

                    <td>
                      {novedad.tipo}
                    </td>

                    <td>
                      {novedad.descripcion}
                    </td>

                    <td>
                      {novedad.fecha}
                    </td>

                    <td>
                      {novedad.hora}
                    </td>

                    <td>
                      <button
  type="button"
  className="btn-evidencias-novedad"
  onClick={() => verEvidencias(novedad)}
>
  Ver evidencias
</button>
                    </td>

                  </tr>
                ))}
              </tbody>

            </table>

          </div>
        )}

{novedadSeleccionada && (
  <div className="panel-evidencias-novedad">

    <div className="encabezado-evidencias-novedad">
      <div>
        <h3>
          Evidencias - Novedad #{novedadSeleccionada.idNovedad}
        </h3>

        <p>
          {novedadSeleccionada.descripcion}
        </p>
      </div>

      <button
        type="button"
        onClick={() => {
          setNovedadSeleccionada(null);
          setEvidencias([]);
        }}
      >
        Cerrar
      </button>
    </div>

    {evidencias.length === 0 ? (
      <div className="sin-alertas">
        Esta novedad no tiene evidencias adjuntas.
      </div>
    ) : (
      <div className="lista-evidencias-consulta">
        {evidencias.map((evidencia) => (
          <div
            key={evidencia.idEvidencia}
            className="evidencia-consulta"
          >
            <strong>
              {evidencia.nombreArchivo}
            </strong>

            <span>
              {evidencia.tipoArchivo}
            </span>
          </div>
        ))}
      </div>
    )}

  </div>
)}

      </div>

    </section>
  );
}

export default GestionNovedades;
import { useEffect, useState } from "react";
import "../PersonalEnServicio.css";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

function GestionReportes() {
  const [reporteActivo, setReporteActivo] = useState("guardas");
  const [datos, setDatos] = useState([]);

  useEffect(() => {
    cargarReporte(reporteActivo);
  }, [reporteActivo]);

  async function cargarReporte(tipo) {
    try {
      let url = "";

      if (tipo === "guardas") {
        url =
          "http://localhost:8080/api/reportes/novedades-guardas";
      }

      if (tipo === "supervisores") {
        url =
          "http://localhost:8080/api/reportes/novedades-supervisores";
      }

if (tipo === "puestos") {
  url =
    "http://localhost:8080/api/reportes/puestos";
}

      const respuesta = await fetch(url);

      if (!respuesta.ok) {
        throw new Error("No fue posible consultar el reporte.");
      }

      const resultado = await respuesta.json();

      setDatos(resultado);
    } catch (error) {
      console.error("Error al cargar reporte:", error);
      setDatos([]);
    }
  }

function exportarPDF() {
  if (datos.length === 0) {
    alert("No hay información para exportar.");
    return;
  }

  const documento = new jsPDF();

  documento.setFontSize(18);
  documento.text("AppGuard - Reporte operativo", 14, 18);

  documento.setFontSize(11);

  const tituloReporte =
    reporteActivo === "guardas"
      ? "Novedades de guardas"
      : reporteActivo === "supervisores"
      ? "Novedades de supervisores"
      : "Reportes de puestos";

  documento.text(tituloReporte, 14, 27);

  if (reporteActivo === "puestos") {
    const filas = datos.map((item) => [
      item.idTurno,
      item.puesto || "",
      item.guarda || "",
      item.codigoGuarda || "",
      item.fecha || "",
      item.horaInicio || "",
      item.horaFin || "",
      item.estado || "",
    ]);

    autoTable(documento, {
      startY: 34,
      head: [[
        "#",
        "Puesto",
        "Guarda",
        "Código",
        "Fecha",
        "Inicio",
        "Fin",
        "Estado",
      ]],
      body: filas,
      styles: {
        fontSize: 8,
        cellPadding: 2,
      },
    });
  } else {
    const filas = datos.map((item) => [
      item.idNovedad,
      item.nombre || "",
      item.documento || "",
      item.tipo || "",
      item.descripcion || "",
      item.fecha || "",
      item.hora || "",
    ]);

    autoTable(documento, {
      startY: 34,
      head: [[
        "#",
        "Nombre",
        "Documento",
        "Tipo",
        "Descripción",
        "Fecha",
        "Hora",
      ]],
      body: filas,
      styles: {
        fontSize: 8,
        cellPadding: 2,
      },
    });
  }

  documento.save(`reporte_${reporteActivo}.pdf`);
}

  return (
    <section className="personal-servicio">

      <div className="encabezado-personal-servicio">
        <div>
          <h2>Reportes operativos</h2>
          <p>
            Consulta información registrada durante la operación.
          </p>
        </div>
      </div>

      <div className="selector-reportes">

        <button
          type="button"
          className={
            reporteActivo === "guardas"
              ? "reporte-seleccionado"
              : ""
          }
          onClick={() => setReporteActivo("guardas")}
        >
          Novedades de guardas
        </button>

        <button
          type="button"
          className={
            reporteActivo === "supervisores"
              ? "reporte-seleccionado"
              : ""
          }
          onClick={() => setReporteActivo("supervisores")}
        >
          Novedades de supervisores
        </button>

        <button
  type="button"
  className={
    reporteActivo === "puestos"
      ? "reporte-seleccionado"
      : ""
  }
  onClick={() => setReporteActivo("puestos")}
>
  Reportes de puestos
</button>

      </div>

      <button
  type="button"
  className="btn-exportar-pdf"
  onClick={exportarPDF}
>
  Exportar PDF
</button>

      <div className="tabla-novedades-contenedor">

        <table className="tabla-novedades">

  <thead>
    {reporteActivo === "puestos" ? (
      <tr>
        <th>#</th>
        <th>Puesto</th>
        <th>Guarda</th>
        <th>Código</th>
        <th>Fecha</th>
        <th>Hora inicio</th>
        <th>Hora fin</th>
        <th>Estado</th>
      </tr>
    ) : (
      <tr>
        <th>#</th>
        <th>Nombre</th>
        <th>Documento</th>
        <th>Tipo</th>
        <th>Descripción</th>
        <th>Fecha</th>
        <th>Hora</th>
      </tr>
    )}
  </thead>

  <tbody>

    {datos.length === 0 ? (
      <tr>
        <td colSpan={reporteActivo === "puestos" ? 8 : 7}>
          No hay información disponible para este reporte.
        </td>
      </tr>
    ) : reporteActivo === "puestos" ? (
      datos.map((item) => (
        <tr key={item.idTurno}>
          <td>{item.idTurno}</td>
          <td>{item.puesto || "No registrado"}</td>
          <td>{item.guarda || "No registrado"}</td>
          <td>{item.codigoGuarda || "No registrado"}</td>
          <td>{item.fecha}</td>
          <td>{item.horaInicio}</td>
          <td>{item.horaFin}</td>
          <td>{item.estado}</td>
        </tr>
      ))
    ) : (
      datos.map((item) => (
        <tr key={item.idNovedad}>
          <td>{item.idNovedad}</td>
          <td>{item.nombre}</td>
          <td>{item.documento}</td>
          <td>{item.tipo}</td>
          <td>{item.descripcion}</td>
          <td>{item.fecha}</td>
          <td>{item.hora}</td>
        </tr>
      ))
    )}

  </tbody>

</table>

      </div>

    </section>
  );
}

export default GestionReportes;
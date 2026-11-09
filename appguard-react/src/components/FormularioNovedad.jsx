import { useState } from "react";
import { registrarNovedad } from "../services/novedadService";
import "../PersonalEnServicio.css";

function FormularioNovedad({ onCerrar }) {
  const [tipo, setTipo] = useState("");
  const [descripcion, setDescripcion] = useState("");
const [archivos, setArchivos] = useState([]);

  async function manejarEnvio(evento) {
    evento.preventDefault();

    if (!tipo) {
  alert("Selecciona el tipo de novedad.");
  return;
}

if (!descripcion.trim()) {
  alert("Escribe la descripción de la novedad.");
  return;
}

if (archivos.length === 0) {
  alert("Debes adjuntar al menos una evidencia.");
  return;
}

    try {
      const novedad = await registrarNovedad(
  {
    idUsuario: 1,
    tipo,
    descripcion,
  },
  archivos
);

      alert(
        `Novedad registrada correctamente\nFecha: ${novedad.fecha}\nHora: ${novedad.hora}\nEstado: ${novedad.estado}`
      );

      setDescripcion("");
      setArchivos([]);
      setTipo("");

      if (onCerrar) {
        onCerrar();
      }
    } catch (error) {
      console.error("Error al registrar novedad:", error);
      alert("No fue posible registrar la novedad.");
    }
  }

  return (
    <section className="personal-servicio">
      <div className="encabezado-personal-servicio">
        <div>
          <h2>Reportar novedad</h2>
          <p>Registra una novedad identificada durante la supervisión.</p>
        </div>

        <button type="button" onClick={onCerrar}>
          Cerrar
        </button>
      </div>

      <form onSubmit={manejarEnvio}>
        <div>
          <label>Tipo de novedad</label>

          <select
  value={tipo}
  onChange={(evento) => setTipo(evento.target.value)}
>
  <option value="" disabled>
    Seleccione tipo de novedad
  </option>

  <option value="Guardas">Guardas</option>
  <option value="Puestos">Puestos</option>
  <option value="Intendencia">Intendencia</option>
  <option value="Otra">Otra</option>
</select>
        </div>

        <div>
          <label>Descripción</label>

          <textarea
            value={descripcion}
            onChange={(evento) => setDescripcion(evento.target.value)}
            placeholder="Describe la novedad identificada"
            rows="5"
          />
        </div>

<div>
  <label className="boton-elegir-archivos">
  Elegir archivos

  <input
    type="file"
    multiple
    hidden
    accept=".pdf,.doc,.docx,.jpg,.jpeg,.png,.mp4,.mov,.mp3,.wav,.m4a"
    onChange={(evento) => {
      const nuevosArchivos = Array.from(evento.target.files);

      const archivosCombinados = [
        ...archivos,
        ...nuevosArchivos,
      ];

      if (archivosCombinados.length > 10) {
        alert("Puedes adjuntar máximo 10 archivos por novedad.");
        evento.target.value = "";
        return;
      }

      setArchivos(archivosCombinados);
      evento.target.value = "";
    }}
  />
</label>

  <small>
    Puedes adjuntar hasta 10 documentos, fotos, videos o audios.
  </small>

  {archivos.length > 0 && (
    <div className="lista-evidencias">
      {archivos.map((archivo, index) => (
        <div className="evidencia-seleccionada" key={`${archivo.name}-${index}`}>
          <span>{archivo.name}</span>

          <button
            type="button"
            onClick={() =>
              setArchivos(
                archivos.filter((_, i) => i !== index)
              )
            }
          >
            Eliminar
          </button>
        </div>
      ))}
    </div>
  )}
</div>

        <button type="submit">
          Registrar novedad
        </button>
      </form>
    </section>
  );
}

export default FormularioNovedad;
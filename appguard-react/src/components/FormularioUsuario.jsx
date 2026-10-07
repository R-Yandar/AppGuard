import { useEffect, useState } from "react";

// Componente para registrar y editar usuarios
function FormularioUsuario({
  usuario,
  onGuardado,
  onCancelar,
}) {

  const [formulario, setFormulario] = useState({
    nombre: "",
    apellido: "",
    documento: "",
    correo: "",
    telefono: "",
    idRol: "",
  });

  // Cargar los datos cuando se edita un usuario
  // o limpiar el formulario cuando se registra uno nuevo
  useEffect(() => {
    if (usuario) {
      setFormulario({
        nombre: usuario.nombre || "",
        apellido: usuario.apellido || "",
        documento: usuario.documento || "",
        correo: usuario.correo || "",
        telefono: usuario.telefono || "",
        idRol: usuario.idRol || "",
      });
    } else {
      setFormulario({
        nombre: "",
        apellido: "",
        documento: "",
        correo: "",
        telefono: "",
        idRol: "",
      });
    }
  }, [usuario]);

  // Actualizar los campos del formulario
  function manejarCambio(evento) {
    const { name, value } = evento.target;

    setFormulario((datosAnteriores) => ({
      ...datosAnteriores,
      [name]: value,
    }));
  }

  // Registrar o editar usuario
  async function manejarEnvio(evento) {
    evento.preventDefault();

    try {
      const esEdicion = Boolean(usuario);

      const datos = {
        nombre: formulario.nombre,
        apellido: formulario.apellido,
        documento: formulario.documento,
        correo: formulario.correo,
        telefono: formulario.telefono,
        idRol: Number(formulario.idRol),
        estado: esEdicion ? usuario.estado : "Activo",
      };

      if (esEdicion) {
        datos.idUsuario = usuario.idUsuario;
      }

      const respuesta = await fetch(
        "http://localhost:8080/api/usuarios",
        {
          method: esEdicion ? "PUT" : "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(datos),
        }
      );

      if (!respuesta.ok) {
        throw new Error(
          esEdicion
            ? "No fue posible actualizar el usuario."
            : "No fue posible registrar el usuario."
        );
      }

      alert(
        esEdicion
          ? "Usuario actualizado correctamente."
          : "Usuario registrado correctamente."
      );

      if (onGuardado) {
        onGuardado();
      }
    } catch (error) {
      console.error("Error al guardar usuario:", error);
      alert("Error al guardar el usuario.");
    }
  }

  return (
    <form
      className="formulario-usuario"
      onSubmit={manejarEnvio}
    >
      <h2>
        {usuario ? "Editar usuario" : "Registrar usuario"}
      </h2>

      <input
        type="text"
        name="nombre"
        placeholder="Nombre"
        value={formulario.nombre}
        onChange={manejarCambio}
        required
      />

      <input
        type="text"
        name="apellido"
        placeholder="Apellido"
        value={formulario.apellido}
        onChange={manejarCambio}
        required
      />

      <input
        type="text"
        name="documento"
        placeholder="Documento"
        value={formulario.documento}
        onChange={manejarCambio}
        required
      />

      <input
        type="email"
        name="correo"
        placeholder="Correo electrónico"
        value={formulario.correo}
        onChange={manejarCambio}
        required
      />

      <input
        type="tel"
        name="telefono"
        placeholder="Teléfono"
        value={formulario.telefono}
        onChange={manejarCambio}
        required
      />

      <select
        name="idRol"
        value={formulario.idRol}
        onChange={manejarCambio}
        required
      >
        <option value="" disabled>
          Seleccione un rol
        </option>

        <option value="1">Administrador</option>
        <option value="2">Supervisor</option>
        <option value="3">Guarda</option>
        <option value="4">Cliente</option>
      </select>

     <div className="acciones-formulario">
  <button
    type="button"
    className="btn-cancelar-formulario"
    onClick={onCancelar}
  >
    Cancelar
  </button>

  <button
    type="submit"
    className="btn-guardar-formulario"
  >
    {usuario ? "Guardar cambios" : "Guardar usuario"}
  </button>
</div>

    </form>
  );
}

export default FormularioUsuario;
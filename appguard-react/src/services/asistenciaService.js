const API_URL = "http://localhost:8080/api/asistencias";

export async function registrarAsistencia(idGuarda) {
  const respuesta = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      idGuarda,
    }),
  });

  if (!respuesta.ok) {
    throw new Error("No fue posible registrar la asistencia.");
  }

  return respuesta.json();
}

export async function listarAsistencias() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar las asistencias.");
  }

  return respuesta.json();
}

export async function consultarEstadoTurno(idGuarda) {
  const respuesta = await fetch(
    `${API_URL}/turno/${idGuarda}`
  );

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar el estado del turno.");
  }

  return respuesta.json();
}

export async function finalizarTurno(idGuarda) {
  const respuesta = await fetch(
    `${API_URL}/finalizar/${idGuarda}`,
    {
      method: "POST",
    }
  );

  if (!respuesta.ok) {
    throw new Error("No fue posible finalizar el turno.");
  }

  return respuesta.json();
}
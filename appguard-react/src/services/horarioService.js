const API_URL = "http://localhost:8080/api/turnos";

export async function listarTurnos() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar los turnos.");
  }

  return respuesta.json();
}

export async function listarTurnosPorFecha(fecha) {
  const respuesta = await fetch(
    `${API_URL}/fecha?fecha=${fecha}`
  );

  if (!respuesta.ok) {
    throw new Error(
      "No fue posible consultar el cronograma."
    );
  }

  return respuesta.json();
}

export async function listarTurnosPorGuarda(idGuarda) {
  const respuesta = await fetch(
    `${API_URL}/guarda/${idGuarda}`
  );

  if (!respuesta.ok) {
    throw new Error(
      "No fue posible consultar el cronograma del guarda."
    );
  }

  return respuesta.json();
}

export async function crearTurno(turno) {
  const respuesta = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(turno),
  });

  if (!respuesta.ok) {
    const mensaje = await respuesta.text();
    throw new Error(mensaje);
  }

  return respuesta.json();
}

export async function actualizarTurno(
  idTurno,
  turno
) {
  const respuesta = await fetch(
    `${API_URL}/${idTurno}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(turno),
    }
  );

  if (!respuesta.ok) {
    const mensaje = await respuesta.text();
    throw new Error(mensaje);
  }

  return respuesta.json();
}

export async function cancelarTurno(idTurno) {
  const respuesta = await fetch(
    `${API_URL}/${idTurno}/cancelar`,
    {
      method: "PUT",
    }
  );

  if (!respuesta.ok) {
    const mensaje = await respuesta.text();
    throw new Error(mensaje);
  }

  return respuesta.json();
}
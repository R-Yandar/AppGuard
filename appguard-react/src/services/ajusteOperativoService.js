const API_URL = "http://localhost:8080/api/ajustes-operativos";

export async function listarTurnos() {
  const respuesta = await fetch(`${API_URL}/turnos`);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar los turnos.");
  }

  return respuesta.json();
}

export async function listarGuardasActivos() {
  const respuesta = await fetch(`${API_URL}/guardas`);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar los guardas activos.");
  }

  return respuesta.json();
}

export async function listarPuestosActivos() {
  const respuesta = await fetch(`${API_URL}/puestos`);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar los puestos activos.");
  }

  return respuesta.json();
}

export async function actualizarTurno(idTurno, turno) {
  const respuesta = await fetch(`${API_URL}/turnos/${idTurno}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(turno),
  });

  if (!respuesta.ok) {
    throw new Error("No fue posible guardar el ajuste operativo.");
  }

  return respuesta.json();
}
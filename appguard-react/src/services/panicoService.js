const API_URL = "http://localhost:8080/api/alertas";

export async function listarAlertas() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar las alertas.");
  }

  return respuesta.json();
}

export async function activarAlerta() {
  const respuesta = await fetch(`${API_URL}/activar`, {
    method: "POST",
  });

  if (!respuesta.ok) {
    throw new Error("No fue posible activar la alerta.");
  }

  return respuesta.json();
}

export async function cancelarAlerta(idAlerta) {
  const respuesta = await fetch(
    `${API_URL}/${idAlerta}/cancelar`,
    {
      method: "PUT",
    }
  );

  if (!respuesta.ok) {
    throw new Error("No fue posible cancelar la alerta.");
  }

  return respuesta.json();
}
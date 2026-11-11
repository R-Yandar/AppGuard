const API_URL = "http://localhost:8080/api/alertas";

export async function listarAlertasActivas() {
  const respuesta = await fetch(`${API_URL}/activas`);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar las alertas activas.");
  }

  return respuesta.json();
}

export async function listarAlertas() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar las alertas.");
  }

  return respuesta.json();
}

export async function atenderAlerta(idAlerta) {
  const respuesta = await fetch(
    `${API_URL}/${idAlerta}/atender`,
    {
      method: "PUT",
    }
  );

  if (!respuesta.ok) {
    throw new Error("No fue posible atender la alerta.");
  }

  return respuesta.json();
}
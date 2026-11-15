const API_URL = "http://localhost:8080/api/guardas";

export async function buscarGuardas(texto) {
  const respuesta = await fetch(
    `${API_URL}/buscar?texto=${encodeURIComponent(texto)}`
  );

  if (!respuesta.ok) {
    throw new Error("No fue posible buscar guardas.");
  }

  return respuesta.json();
}

export async function listarGuardas() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar los guardas.");
  }

  return respuesta.json();
}
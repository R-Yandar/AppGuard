const API_URL = "http://localhost:8080/api/novedades";

export async function registrarNovedad(novedad, archivos = []) {
  const formData = new FormData();

  formData.append("idUsuario", novedad.idUsuario);
  formData.append("tipo", novedad.tipo);
  formData.append("descripcion", novedad.descripcion);

  archivos.forEach((archivo) => {
    formData.append("archivos", archivo);
  });

  const respuesta = await fetch(API_URL, {
    method: "POST",
    body: formData,
  });

  if (!respuesta.ok) {
    throw new Error("No fue posible registrar la novedad.");
  }

  return respuesta.json();
}

export async function listarNovedades() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar las novedades.");
  }

  return respuesta.json();
}
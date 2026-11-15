const API_URL = "http://localhost:8080/api/permisos";

export async function listarPermisosPorRol(idRol) {
  const respuesta = await fetch(
    `${API_URL}/rol/${idRol}`
  );

  if (!respuesta.ok) {
    throw new Error(
      "No fue posible consultar los permisos del rol."
    );
  }

  return respuesta.json();
}

export async function tienePermiso(idRol, modulo) {
  const permisos = await listarPermisosPorRol(idRol);

  return permisos.some(
    (permiso) =>
      permiso.modulo === modulo &&
      permiso.permitido === true
  );
}
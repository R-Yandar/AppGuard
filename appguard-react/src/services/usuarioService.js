/**
 * usuarioService
 *
 * Centraliza la comunicación entre el front-end de AppGuard
 * y la API REST de usuarios desarrollada con Spring Boot.
 *
 * Funcionalidades:
 * - Consultar usuarios.
 * - Registrar usuarios.
 * - Actualizar usuarios.
 * - Eliminar usuarios.
 *
 * Este archivo evita que los componentes conozcan directamente
 * la URL y los detalles de comunicación con el back-end.
 */

const API_URL = "http://localhost:8080/api/usuarios";

// Consultar todos los usuarios registrados
export async function listarUsuarios() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar los usuarios.");
  }

  return respuesta.json();
}

// Registrar un nuevo usuario
export async function crearUsuario(usuario) {
  const respuesta = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(usuario),
  });

  if (!respuesta.ok) {
    throw new Error("No fue posible registrar el usuario.");
  }

  return respuesta.json();
}

// Actualizar un usuario existente
export async function actualizarUsuario(usuario) {
  const respuesta = await fetch(API_URL, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(usuario),
  });

  if (!respuesta.ok) {
    throw new Error("No fue posible actualizar el usuario.");
  }

  return respuesta.json();
}

// Eliminar un usuario por su ID
export async function eliminarUsuario(idUsuario) {
  const respuesta = await fetch(`${API_URL}/${idUsuario}`, {
    method: "DELETE",
  });

  if (!respuesta.ok) {
    throw new Error("No fue posible eliminar el usuario.");
  }
}
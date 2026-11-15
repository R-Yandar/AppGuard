const API_URL = "http://localhost:8080/api/clientes";

export async function listarClientes() {
  const respuesta = await fetch(API_URL);

  if (!respuesta.ok) {
    throw new Error("No fue posible consultar los clientes.");
  }

  return respuesta.json();
}

export async function buscarClientes(texto) {
  const respuesta = await fetch(
    `${API_URL}/buscar?texto=${encodeURIComponent(texto)}`
  );

  if (!respuesta.ok) {
    throw new Error("No fue posible buscar clientes.");
  }

  return respuesta.json();
}

export async function crearCliente(cliente) {
  const respuesta = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(cliente),
  });

  if (!respuesta.ok) {
    const mensaje = await respuesta.text();
    throw new Error(mensaje);
  }

  return respuesta.json();
}

export async function actualizarCliente(idCliente, cliente) {
  const respuesta = await fetch(
    `${API_URL}/${idCliente}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(cliente),
    }
  );

  if (!respuesta.ok) {
    const mensaje = await respuesta.text();
    throw new Error(mensaje);
  }

  return respuesta.json();
}

export async function cambiarEstadoCliente(idCliente) {
  const respuesta = await fetch(
    `${API_URL}/${idCliente}/estado`,
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
export async function listarPuestosCliente(idCliente) {
  const respuesta = await fetch(
    `${API_URL}/${idCliente}/puestos`
  );

  if (!respuesta.ok) {
    throw new Error(
      "No fue posible consultar los puestos del cliente."
    );
  }

  return respuesta.json();
}
export async function listarPersonalPuesto(idPuesto) {
  const respuesta = await fetch(
    `http://localhost:8080/api/turnos/puesto/${idPuesto}/personal`
  );

  if (!respuesta.ok) {
    throw new Error(
      "No fue posible consultar el personal asignado al puesto."
    );
  }

  return respuesta.json();
}
import { useEffect, useState } from "react";

import {
  listarClientes,
  buscarClientes,
  listarPuestosCliente,
  listarPersonalPuesto,
} from "../services/clienteService";

import "../PersonalEnServicio.css";

function GestionClientes() {
  const [clientes, setClientes] = useState([]);
  const [textoBusqueda, setTextoBusqueda] = useState("");

  const [clienteSeleccionado, setClienteSeleccionado] =
    useState(null);

  const [puestos, setPuestos] = useState([]);

  const [puestoSeleccionado, setPuestoSeleccionado] =
    useState(null);

  const [personalPuesto, setPersonalPuesto] = useState([]);

  const [cargando, setCargando] = useState(false);
  const [cargandoPuestos, setCargandoPuestos] =
    useState(false);

  const [cargandoPersonal, setCargandoPersonal] =
    useState(false);

  useEffect(() => {
    cargarClientes();
  }, []);

  async function cargarClientes() {
    try {
      setCargando(true);

      const datos = await listarClientes();

      setClientes(datos);
    } catch (error) {
      alert(error.message);
    } finally {
      setCargando(false);
    }
  }

  async function manejarBusqueda() {
    if (!textoBusqueda.trim()) {
      await cargarClientes();
      return;
    }

    try {
      setCargando(true);

      const datos = await buscarClientes(
        textoBusqueda
      );

      setClientes(datos);
    } catch (error) {
      alert(error.message);
    } finally {
      setCargando(false);
    }
  }

  async function limpiarBusqueda() {
    setTextoBusqueda("");
    setClienteSeleccionado(null);
    setPuestos([]);
    setPuestoSeleccionado(null);
    setPersonalPuesto([]);

    await cargarClientes();
  }

  async function seleccionarCliente(cliente) {
    try {
      setClienteSeleccionado(cliente);

      setPuestos([]);
      setPuestoSeleccionado(null);
      setPersonalPuesto([]);

      setCargandoPuestos(true);

      const datos = await listarPuestosCliente(
        cliente.idCliente
      );

      setPuestos(datos);
    } catch (error) {
      alert(error.message);
    } finally {
      setCargandoPuestos(false);
    }
  }

  async function seleccionarPuesto(puesto) {
    try {
      setPuestoSeleccionado(puesto);
      setPersonalPuesto([]);

      setCargandoPersonal(true);

      const datos = await listarPersonalPuesto(
        puesto.idPuesto
      );

      setPersonalPuesto(datos);
    } catch (error) {
      alert(error.message);
    } finally {
      setCargandoPersonal(false);
    }
  }

  function formatearFecha(fecha) {
    if (!fecha) {
      return "";
    }

    const [anio, mes, dia] = fecha.split("-");

    return `${dia}/${mes}/${anio}`;
  }

  function formatearHora(hora) {
    if (!hora) {
      return "";
    }

    return hora.substring(0, 5);
  }

  return (
    <section className="personal-servicio">

      <div className="encabezado-personal-servicio">
        <div>
          <h2>Consulta de clientes</h2>

          <p>
            Consulta los clientes, puestos de servicio y personal asignado.
          </p>
        </div>
      </div>

      <div className="buscador-clientes">

        <div className="campo-buscador-clientes">

          <label>
            Buscar cliente
          </label>

          <input
            type="text"
            value={textoBusqueda}
            onChange={(evento) =>
              setTextoBusqueda(
                evento.target.value
              )
            }
            onKeyDown={(evento) => {
              if (evento.key === "Enter") {
                manejarBusqueda();
              }
            }}
            placeholder="Nombre o NIT"
          />

        </div>

        <button
          type="button"
          className="btn-principal"
          onClick={manejarBusqueda}
        >
          {cargando
            ? "Buscando..."
            : "Buscar"}
        </button>

        <button
          type="button"
          className="btn-secundario"
          onClick={limpiarBusqueda}
        >
          Limpiar
        </button>

      </div>

      <div className="lista-clientes">

        {clientes.length === 0 ? (
          <div className="mensaje-clientes-vacio">
            No se encontraron clientes.
          </div>
        ) : (
          clientes.map((cliente) => (

            <button
              type="button"
              key={cliente.idCliente}
              className={`tarjeta-cliente ${
                clienteSeleccionado?.idCliente ===
                cliente.idCliente
                  ? "cliente-seleccionado"
                  : ""
              }`}
              onClick={() =>
                seleccionarCliente(cliente)
              }
            >

              <div className="cliente-principal">

                <strong>
                  {cliente.nombreCliente}
                </strong>

                <span>
                  NIT:{" "}
                  {cliente.nit ||
                    "No registrado"}
                </span>

              </div>

              <div className="cliente-contacto">

                <span>
                  {cliente.direccion ||
                    "Sin dirección"}
                </span>

                <span>
                  {cliente.telefono ||
                    "Sin teléfono"}
                </span>

                <span>
                  {cliente.correo ||
                    "Sin correo"}
                </span>

              </div>

              <span
                className={`estado-cliente ${
                  cliente.estado === "Activo"
                    ? "cliente-activo"
                    : "cliente-inactivo"
                }`}
              >
                {cliente.estado}
              </span>

            </button>
          ))
        )}

      </div>

      {clienteSeleccionado && (
        <div className="detalle-cliente">

          <div className="encabezado-detalle-cliente">

            <div>

              <span>
                Cliente seleccionado
              </span>

              <h3>
                {
                  clienteSeleccionado
                    .nombreCliente
                }
              </h3>

            </div>

            <span
              className={`estado-cliente ${
                clienteSeleccionado.estado ===
                "Activo"
                  ? "cliente-activo"
                  : "cliente-inactivo"
              }`}
            >
              {
                clienteSeleccionado.estado
              }
            </span>

          </div>

          <div className="resumen-cliente">

            <div>
              <span>NIT</span>

              <strong>
                {clienteSeleccionado.nit ||
                  "No registrado"}
              </strong>
            </div>

            <div>
              <span>Dirección</span>

              <strong>
                {
                  clienteSeleccionado.direccion ||
                  "No registrada"
                }
              </strong>
            </div>

            <div>
              <span>Teléfono</span>

              <strong>
                {
                  clienteSeleccionado.telefono ||
                  "No registrado"
                }
              </strong>
            </div>

            <div>
              <span>Correo</span>

              <strong>
                {
                  clienteSeleccionado.correo ||
                  "No registrado"
                }
              </strong>
            </div>

          </div>

          <div className="seccion-puestos-cliente">

            <div className="titulo-puestos-cliente">

              <h3>
                Puestos de servicio
              </h3>

              <span>
                {puestos.length}{" "}
                {puestos.length === 1
                  ? "puesto"
                  : "puestos"}
              </span>

            </div>

            {cargandoPuestos ? (

              <p className="mensaje-puestos-cliente">
                Consultando puestos...
              </p>

            ) : puestos.length === 0 ? (

              <p className="mensaje-puestos-cliente">
                Este cliente no tiene puestos
                de servicio registrados.
              </p>

            ) : (

              <div className="lista-puestos-cliente">

                {puestos.map((puesto) => (

                  <button
                    type="button"
                    key={puesto.idPuesto}
                    className={`tarjeta-puesto-cliente ${
                      puestoSeleccionado?.idPuesto ===
                      puesto.idPuesto
                        ? "puesto-seleccionado"
                        : ""
                    }`}
                    onClick={() =>
                      seleccionarPuesto(puesto)
                    }
                  >

                    <div className="puesto-cliente-principal">

                      <strong>
                        {puesto.nombrePuesto}
                      </strong>

                      <span>
                        {puesto.ciudad ||
                          "Ciudad no registrada"}
                      </span>

                    </div>

                    <div className="puesto-cliente-detalle">

                      <span>
                        Dirección
                      </span>

                      <strong>
                        {puesto.direccion ||
                          "No registrada"}
                      </strong>

                    </div>

                    <span
                      className={`estado-puesto-cliente ${
                        puesto.estado === "Activo"
                          ? "puesto-activo"
                          : "puesto-inactivo"
                      }`}
                    >
                      {puesto.estado}
                    </span>

                  </button>
                ))}

              </div>
            )}

          </div>

          {puestoSeleccionado && (
            <div className="seccion-personal-puesto">

              <div className="titulo-personal-puesto">

                <div>
                  <span>
                    Puesto seleccionado
                  </span>

                  <h3>
                    {puestoSeleccionado.nombrePuesto}
                  </h3>
                </div>

                <span>
                  {personalPuesto.length}{" "}
                  {personalPuesto.length === 1
                    ? "asignación"
                    : "asignaciones"}
                </span>

              </div>

              {cargandoPersonal ? (

                <p className="mensaje-personal-puesto">
                  Consultando personal asignado...
                </p>

              ) : personalPuesto.length === 0 ? (

                <p className="mensaje-personal-puesto">
                  No hay personal programado para este puesto.
                </p>

              ) : (

                <div className="tabla-responsive">

                  <table className="tabla-appguard">

                    <thead>
                      <tr>
                        <th>Guarda</th>
                        <th>Documento</th>
                        <th>Código</th>
                        <th>Fecha</th>
                        <th>Hora inicio</th>
                        <th>Hora fin</th>
                        <th>Estado</th>
                      </tr>
                    </thead>

                    <tbody>

                      {personalPuesto.map((asignacion) => (
                        <tr key={asignacion.idTurno}>

                          <td>
                            {asignacion.nombreCompleto}
                          </td>

                          <td>
                            {asignacion.documento}
                          </td>

                          <td>
                            {asignacion.codigoGuarda}
                          </td>

                          <td>
                            {formatearFecha(
                              asignacion.fecha
                            )}
                          </td>

                          <td>
                            {formatearHora(
                              asignacion.horaInicio
                            )}
                          </td>

                          <td>
                            {formatearHora(
                              asignacion.horaFin
                            )}
                          </td>

                          <td>
                            <span
                              className={`estado-turno estado-${asignacion.estado?.toLowerCase()}`}
                            >
                              {asignacion.estado}
                            </span>
                          </td>

                        </tr>
                      ))}

                    </tbody>

                  </table>

                </div>
              )}

            </div>
          )}

        </div>
      )}

    </section>
  );
}

export default GestionClientes;
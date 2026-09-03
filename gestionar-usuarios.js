/* =========================================================
   APPGUARD - GESTIÓN DE USUARIOS
   Conexión con API Java + JDBC + MySQL
   ========================================================= */

const API_URL = "http://localhost:8080/AppGuard/usuarios?formato=json";

let usuarios = [];

const tablaUsuarios = document.getElementById("tablaUsuarios");
const buscarUsuario = document.getElementById("buscarUsuario");
const btnNuevoUsuario = document.getElementById("btnNuevoUsuario");
const totalUsuarios = document.getElementById("totalUsuarios");
const usuariosActivos = document.getElementById("usuariosActivos");


/* =========================================================
   ROLES
   ========================================================= */

function obtenerNombreRol(idRol) {

    const roles = {
        1: "Administrador",
        2: "Supervisor",
        3: "Guarda",
        4: "Cliente"
    };

    return roles[idRol] || "Sin rol";
}


/* =========================================================
   CARGAR USUARIOS DESDE MYSQL
   ========================================================= */

async function cargarUsuarios() {

    try {

        const respuesta = await fetch(API_URL);

        if (!respuesta.ok) {
            throw new Error("No fue posible consultar los usuarios.");
        }

        usuarios = await respuesta.json();

        mostrarUsuarios(usuarios);

    } catch (error) {
    console.error(error);
}

}


/* =========================================================
   MOSTRAR USUARIOS
   ========================================================= */

function mostrarUsuarios(lista) {

    tablaUsuarios.innerHTML = "";

    lista.forEach(usuario => {

        const rol = obtenerNombreRol(usuario.idRol);

        const iniciales =
            usuario.nombre.charAt(0).toUpperCase() +
            usuario.apellido.charAt(0).toUpperCase();

        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>
                <div class="usuario-tabla">

                    <div class="mini-avatar">
                        ${iniciales}
                    </div>

                    <div>
                        <strong>
                            ${usuario.nombre} ${usuario.apellido}
                        </strong>

                        <small>
                            ${usuario.telefono || ""}
                        </small>
                    </div>

                </div>
            </td>

            <td>${usuario.documento}</td>

            <td>${usuario.correo}</td>

            <td>
                <span class="rol ${rol.toLowerCase()}">
                    ${rol}
                </span>
            </td>

            <td>
                <span class="estado ${usuario.estado.toLowerCase()}">
                    ${usuario.estado}
                </span>
            </td>

            <td>
                <div class="acciones-tabla">

                    <button
                        class="btn-accion editar"
                        onclick="editarUsuario(${usuario.idUsuario})"
                        title="Editar usuario">

                        <i class="bi bi-pencil-square"></i>

                    </button>

                    <button
                        class="btn-accion estado-usuario"
                        onclick="cambiarEstado(${usuario.idUsuario})"
                        title="Cambiar estado">

                        <i class="bi bi-person-check"></i>

                    </button>

                    <button
                        class="btn-accion eliminar"
                        onclick="eliminarUsuario(${usuario.idUsuario})"
                        title="Eliminar usuario">

                        <i class="bi bi-trash3"></i>

                    </button>

                </div>
            </td>
        `;

        tablaUsuarios.appendChild(fila);
    });

    actualizarResumen();
}


/* =========================================================
   RESUMEN
   ========================================================= */

function actualizarResumen() {

    totalUsuarios.textContent = usuarios.length;

    usuariosActivos.textContent =
        usuarios.filter(
            usuario => usuario.estado === "Activo"
        ).length;
}


/* =========================================================
   BUSCADOR
   ========================================================= */

buscarUsuario.addEventListener("input", function () {

    const texto =
        buscarUsuario.value
            .toLowerCase()
            .trim();

    const resultado = usuarios.filter(usuario => {

        const rol =
            obtenerNombreRol(usuario.idRol)
                .toLowerCase();

        return (
            usuario.nombre.toLowerCase().includes(texto) ||
            usuario.apellido.toLowerCase().includes(texto) ||
            usuario.documento.toLowerCase().includes(texto) ||
            usuario.correo.toLowerCase().includes(texto) ||
            usuario.estado.toLowerCase().includes(texto) ||
            rol.includes(texto)
        );
    });

    mostrarUsuarios(resultado);
});


/* =========================================================
   CARGA INICIAL
   ========================================================= */

cargarUsuarios();
/* =========================================================
   CREAR NUEVO USUARIO
   ========================================================= */

btnNuevoUsuario.addEventListener("click", function () {
    window.location.href = "http://localhost:8080/AppGuard/nuevo-usuario.html";
});

/* =========================================================
   EDITAR USUARIO
   ========================================================= */

async function editarUsuario(idUsuario) {

    const usuario = usuarios.find(
        usuario => usuario.idUsuario === idUsuario
    );

    if (!usuario) return;

    const nombre = prompt(
        "Nombre:",
        usuario.nombre
    );

    if (!nombre) return;

    const apellido = prompt(
        "Apellido:",
        usuario.apellido
    );

    if (!apellido) return;

    const documento = prompt(
        "Documento:",
        usuario.documento
    );

    if (!documento) return;

    const correo = prompt(
        "Correo:",
        usuario.correo
    );

    if (!correo) return;

    const telefono = prompt(
        "Teléfono:",
        usuario.telefono
    );

    if (!telefono) return;

    const rolIngresado = prompt(
        "Rol:\n" +
        "1 = Administrador\n" +
        "2 = Supervisor\n" +
        "3 = Guarda\n" +
        "4 = Cliente",
        usuario.idRol
    );

    const idRol = Number(rolIngresado);

    if (![1, 2, 3, 4].includes(idRol)) {
        alert("Rol no válido.");
        return;
    }

    const datos = new URLSearchParams();

    datos.append("idUsuario", usuario.idUsuario);
    datos.append("idRol", idRol);
    datos.append("nombre", nombre);
    datos.append("apellido", apellido);
    datos.append("documento", documento);
    datos.append("correo", correo);
    datos.append("telefono", telefono);
    datos.append("estado", usuario.estado);

    try {

        const respuesta = await fetch(API_URL, {
            method: "PUT",
            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded"
            },
            body: datos.toString()
        });

        if (!respuesta.ok) {
            throw new Error(
                "No fue posible actualizar el usuario."
            );
        }

        alert("Usuario actualizado correctamente.");

        await cargarUsuarios();

    } catch (error) {

        console.error(error);

        alert(
            "Error al actualizar el usuario."
        );
    }
}


/* =========================================================
   CAMBIAR ESTADO
   ========================================================= */

async function cambiarEstado(idUsuario) {

    const usuario = usuarios.find(
        usuario => usuario.idUsuario === idUsuario
    );

    if (!usuario) return;

    const nuevoEstado =
        usuario.estado === "Activo"
            ? "Inactivo"
            : "Activo";

    const datos = new URLSearchParams();

    datos.append("idUsuario", usuario.idUsuario);
    datos.append("idRol", usuario.idRol);
    datos.append("nombre", usuario.nombre);
    datos.append("apellido", usuario.apellido);
    datos.append("documento", usuario.documento);
    datos.append("correo", usuario.correo);
    datos.append("telefono", usuario.telefono);
    datos.append("estado", nuevoEstado);

    try {

        const respuesta = await fetch(API_URL, {
            method: "PUT",
            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded"
            },
            body: datos.toString()
        });

        if (!respuesta.ok) {
            throw new Error(
                "No fue posible cambiar el estado."
            );
        }

        await cargarUsuarios();

    } catch (error) {

        console.error(error);

        alert(
            "Error al cambiar el estado."
        );
    }
}


/* =========================================================
   ELIMINAR USUARIO
   ========================================================= */

async function eliminarUsuario(idUsuario) {

    const usuario = usuarios.find(
        usuario => usuario.idUsuario === idUsuario
    );

    if (!usuario) return;

    const confirmar = confirm(
        `¿Desea eliminar a ${usuario.nombre} ${usuario.apellido}?`
    );

    if (!confirmar) return;

    const datos = new URLSearchParams();

    datos.append("idUsuario", idUsuario);

    try {

        const respuesta = await fetch(API_URL, {
            method: "DELETE",
            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded"
            },
            body: datos.toString()
        });

        if (!respuesta.ok) {
            throw new Error(
                "No fue posible eliminar el usuario."
            );
        }

        alert("Usuario eliminado correctamente.");

        await cargarUsuarios();

    } catch (error) {

        console.error(error);

        alert(
            "Error al eliminar el usuario."
        );
    }
}
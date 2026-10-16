const form = document.getElementById("loginForm");

form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const documento = document.getElementById("documento").value.trim();
    const password = document.getElementById("password").value.trim();

    if (documento === "" || password === "") {
        alert("Todos los campos son obligatorios");
        return;
    }

    try {
        const respuesta = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                documento: documento,
                contrasena: password
            })
        });

        const datos = await respuesta.json();

        if (!respuesta.ok) {
            alert(datos.mensaje || "Error en la autenticación.");
            return;
        }

        alert(datos.mensaje);

        localStorage.setItem("idUsuario", datos.idUsuario);
        localStorage.setItem("nombreUsuario", datos.nombre);
        localStorage.setItem("apellidoUsuario", datos.apellido);
        localStorage.setItem("idRol", datos.idRol);

        window.location.href = "panel.html";

    } catch (error) {
        console.error("Error al iniciar sesión:", error);
        alert("No fue posible conectar con el servidor.");
    }
});

const togglePassword = document.getElementById("togglePassword");
const passwordInput = document.getElementById("password");

if (togglePassword && passwordInput) {
    togglePassword.addEventListener("click", function () {

        const mostrar = passwordInput.type === "password";

        passwordInput.type = mostrar ? "text" : "password";

        const icono = togglePassword.querySelector("i");

        icono.classList.toggle("bi-eye", mostrar);
        icono.classList.toggle("bi-eye-slash", !mostrar);
    });
}
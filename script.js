const form = document.getElementById("loginForm");

form.addEventListener("submit", function (e) {
    e.preventDefault();

    const documento = document.getElementById("documento").value.trim();
    const password = document.getElementById("password").value.trim();

    if (documento === "" || password === "") {
        alert("Todos los campos son obligatorios");
    } else {
        window.location.href = "panel.html";
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
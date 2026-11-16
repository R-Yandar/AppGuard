import { useState } from "react";

import Encabezado from "./components/Encabezado";
import MenuLateral from "./components/MenuLateral";
import MenuPrincipal from "./components/MenuPrincipal";

import GestionGuardas from "./components/GestionGuardas";
import GestionSupervision from "./components/GestionSupervision";
import GestionPanico from "./components/GestionPanico";
import GestionNovedades from "./components/GestionNovedades";
import GestionReportes from "./components/GestionReportes";
import GestionAdministrador from "./components/GestionAdministrador";
import GestionHorarios from "./components/GestionHorarios";
import GestionClientes from "./components/GestionClientes";

import "./App.css";
import "./PersonalEnServicio.css";

function App() {
  const [moduloActivo, setModuloActivo] = useState("inicio");

  const idRol = 1;

  function volverInicio() {
    setModuloActivo("inicio");
  }

  function obtenerTitulo() {
    switch (moduloActivo) {
      case "guardas":
        return "Guardas";

      case "supervision":
        return "Supervisión";

      case "panico":
        return "Botón de pánico";

      case "novedades":
        return "Novedades";

      case "reportes":
        return "Reportes";

      case "administrador":
        return "Administrador";

      case "horarios":
        return "Horarios";

      case "clientes":
        return "Clientes";

      default:
        return "Inicio";
    }
  }

  function obtenerDescripcion() {
    switch (moduloActivo) {
      case "guardas":
        return "Gestión y control del servicio de guardas";

      case "supervision":
        return "Control y seguimiento de la operación";

      case "panico":
        return "Gestión de alertas de emergencia";

      case "novedades":
        return "Registro y seguimiento de novedades operativas";

      case "reportes":
        return "Consulta y análisis de la operación";

      case "administrador":
        return "Gestión y configuración del sistema";

      case "horarios":
        return "Programación y consulta de cronogramas";

      case "clientes":
        return "Consulta de servicios, puestos y personal asignado";

      default:
        return "Control operativo inteligente";
    }
  }

  function renderizarModulo() {
    switch (moduloActivo) {
      case "guardas":
        return <GestionGuardas />;

      case "supervision":
        return <GestionSupervision />;

      case "panico":
        return <GestionPanico />;

      case "novedades":
        return <GestionNovedades />;

      case "reportes":
        return <GestionReportes />;

      case "administrador":
        return <GestionAdministrador />;

      case "horarios":
  return <GestionHorarios />;

      case "clientes":
  return <GestionClientes />;

      default:
        return (
          <MenuPrincipal
            idRol={idRol}
            onSeleccionarModulo={setModuloActivo}
          />
        );
    }
  }

  return (
    <div className="pagina-modulo">

      <MenuLateral />

      <main className="contenido-modulo">

        {moduloActivo !== "inicio" && (
          <Encabezado
            titulo={obtenerTitulo()}
            descripcion={obtenerDescripcion()}
            onVolver={volverInicio}
          />
        )}

        {renderizarModulo()}

      </main>

    </div>
  );
}

export default App;
import Encabezado from "./components/Encabezado";
import MenuLateral from "./components/MenuLateral";
import GestionGuardas from "./components/GestionGuardas";
import "./App.css";

// Componente principal de AppGuard
function App() {
  return (
    <div className="pagina-modulo">
      <MenuLateral />

      <main className="contenido-modulo">
        <Encabezado />
        <GestionGuardas />
      </main>
    </div>
  );
}

export default App;
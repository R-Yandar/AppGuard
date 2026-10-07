import Encabezado from "./components/Encabezado";
import MenuLateral from "./components/MenuLateral";
import GestionUsuarios from "./components/GestionUsuarios";
import "./App.css";

// Componente principal de AppGuard
function App() {
  return (
    <div className="pagina-modulo">
      <MenuLateral />

      <main className="contenido-modulo">
        <Encabezado />
        <GestionUsuarios />
      </main>
    </div>
  );
}

export default App;
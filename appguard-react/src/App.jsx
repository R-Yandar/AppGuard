import Encabezado from "./components/Encabezado";
import MenuLateral from "./components/MenuLateral";
import GestionSupervision from "./components/GestionSupervision";
import "./App.css";

// Componente principal de AppGuard
function App() {
  return (
    <div className="pagina-modulo">
      <MenuLateral />

      <main className="contenido-modulo">
        <Encabezado
  titulo="Supervisión"
  descripcion="Control y seguimiento de la operación"
/>
        <GestionSupervision />
      </main>
    </div>
  );
}

export default App;
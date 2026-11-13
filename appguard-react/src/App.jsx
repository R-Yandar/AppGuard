import Encabezado from "./components/Encabezado";
import MenuLateral from "./components/MenuLateral";
import GestionSupervision from "./components/GestionSupervision";
import GestionPanico from "./components/GestionPanico";
import GestionNovedades from "./components/GestionNovedades";
import "./App.css";

// Componente principal de AppGuard
function App() {
  return (
    <div className="pagina-modulo">
      <MenuLateral />

      <main className="contenido-modulo">
       <Encabezado
  titulo="Novedades"
  descripcion="Registro y seguimiento de novedades operativas"
/>

<GestionNovedades />
      </main>
    </div>
  );
}

export default App;
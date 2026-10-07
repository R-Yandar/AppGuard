// Componente encargado de buscar usuarios
function BuscadorUsuarios({ busqueda, onBuscar }) {
  return (
    <div className="buscador-usuarios">
      <input
        type="text"
        placeholder="Buscar usuario..."
        value={busqueda}
        onChange={(e) => onBuscar(e.target.value)}
      />
    </div>
  );
}

export default BuscadorUsuarios;
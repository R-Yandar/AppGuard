/**
 * BuscadorUsuarios
 *
 * Permite buscar y filtrar los usuarios registrados en AppGuard
 * a partir del texto ingresado por el administrador.
 *
 * Funcionalidad/HU: consultar y buscar usuarios registrados.
 *
 * Props:
 * - busqueda: texto actual escrito en el buscador.
 * - onBuscar: función que actualiza el criterio de búsqueda.
 */

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
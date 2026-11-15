function Encabezado({ titulo, descripcion, onVolver }) {
  return (
    <header className="encabezado-modulo">
      <div className="titulo-modulo">

        <button
          type="button"
          className="volver-panel"
          onClick={onVolver}
          aria-label="Volver al menú principal"
        >
          ←
        </button>

        <div>
          <h1>{titulo}</h1>
          <p>{descripcion}</p>
        </div>
      </div>

      <div className="usuario-modulo">
        <div>
          <strong>BRAYAN RODRIGUEZ</strong>
          <span>Operador de seguridad</span>
          <span className="estado-usuario">
            Activo
          </span>
        </div>
      </div>
    </header>
  );
}

export default Encabezado;
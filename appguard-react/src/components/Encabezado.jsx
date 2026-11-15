function Encabezado({ titulo, descripcion, onVolver }) {
  return (
    <header className="encabezado-modulo">
      <div className="titulo-modulo">

        {onVolver && (
          <button
            type="button"
            className="boton-volver-encabezado"
            onClick={onVolver}
            aria-label="Volver al menú principal"
          >
            <i className="bi bi-arrow-left"></i>
          </button>
        )}

        <div>
          <h1>{titulo}</h1>
          <p>{descripcion}</p>
        </div>

      </div>

      <div className="usuario-modulo">
        <div>
          <strong>BRAYAN RODRIGUEZ</strong>
          <span>Operador de seguridad</span>
          <span className="estado-usuario">Activo</span>
        </div>
      </div>
    </header>
  );
}

export default Encabezado;
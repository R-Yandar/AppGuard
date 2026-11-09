function Encabezado({ titulo, descripcion }) {
  return (
    <header className="encabezado-modulo">
      <div className="titulo-modulo">
        <span className="volver-panel">←</span>

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
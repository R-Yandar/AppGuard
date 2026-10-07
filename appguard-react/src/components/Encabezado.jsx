// Encabezado del módulo Administrador
function Encabezado() {
  return (
    <header className="encabezado-modulo">
      <div className="titulo-modulo">
        <span className="volver-panel">←</span>

        <div>
          <h1>Administrador</h1>
          <p>Administración de usuarios y operación</p>
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
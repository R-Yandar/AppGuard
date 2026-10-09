/**
 * MenuLateral
 *
 * Muestra el menú principal de navegación del módulo Administrador
 * de AppGuard y el estado general del sistema.
 *
 * Funcionalidad/HU:
 * - Navegar entre las opciones principales del sistema.
 * - Mostrar el estado activo y protegido de AppGuard.
 *
 * Props:
 * - Este componente no recibe props actualmente.
 */

function MenuLateral() {
  return (
    <aside className="sidebar-modulo">
      <div className="marca-modulo">
  <img
    src="/img/logo sin fondo.png"
    alt="Logo AppGuard"
    className="logo-modulo"
  />

  <p>Control Operativo Inteligente</p>
</div>

      <nav className="menu-modulo">
        <a href="#">Inicio</a>
        <a href="#">Notificaciones</a>
        <a href="#">Configuraciones</a>
        <a href="#">Ayuda</a>
        <a href="#">Cerrar sesión</a>
      </nav>

      <div className="estado-modulo">
        <div>
          Sistema activo <span className="punto-verde"></span>
          <br />
          y Protegido
        </div>
      </div>
    </aside>
  );
}

export default MenuLateral;
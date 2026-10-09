# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Oxc](https://oxc.rs)
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/)

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.

## Módulo desarrollado

Para la evidencia GA7-220501096-AA4-EV03 se desarrolló el componente
front-end correspondiente al módulo **Administrador - Gestión de Usuarios**
del sistema AppGuard.

El módulo permite consultar, buscar, registrar, modificar, cambiar el estado
y eliminar usuarios, mediante una interfaz desarrollada en React y conectada
con una API REST desarrollada en Spring Boot.

## Estructura de componentes

El módulo Gestión de Usuarios está organizado mediante los siguientes componentes:

- `GestionUsuarios.jsx`: componente principal que coordina el módulo.
- `BuscadorUsuarios.jsx`: permite buscar usuarios registrados.
- `ListaUsuarios.jsx`: muestra la tabla de usuarios.
- `UsuarioItem.jsx`: representa cada usuario y sus acciones.
- `FormularioUsuario.jsx`: permite registrar y modificar usuarios.
- `Encabezado.jsx`: muestra la información principal del módulo.
- `MenuLateral.jsx`: contiene las opciones de navegación del administrador.

La comunicación con el back-end se encuentra centralizada en:

- `services/usuarioService.js`

## Trazabilidad funcional

| Funcionalidad | Componente principal | Archivo | Endpoint |
|---|---|---|---|
| Consultar usuarios | GestionUsuarios / ListaUsuarios | `GestionUsuarios.jsx` | GET `/api/usuarios` |
| Buscar usuarios | BuscadorUsuarios | `BuscadorUsuarios.jsx` | Filtrado realizado en React |
| Registrar usuario | FormularioUsuario | `FormularioUsuario.jsx` | POST `/api/usuarios` |
| Modificar usuario | FormularioUsuario | `FormularioUsuario.jsx` | PUT `/api/usuarios` |
| Cambiar estado | GestionUsuarios / UsuarioItem | `GestionUsuarios.jsx` | PUT `/api/usuarios` |
| Eliminar usuario | UsuarioItem / GestionUsuarios | `GestionUsuarios.jsx` | DELETE `/api/usuarios/{idUsuario}` |

## Tecnologías utilizadas

- React
- Vite
- JavaScript
- HTML
- CSS
- Spring Boot
- MySQL
- Git
- GitHub

## Comunicación con el back-end

Las solicitudes HTTP se gestionan desde el archivo:

`src/services/usuarioService.js`

Los componentes no contienen directamente las URL de la API, permitiendo
una mejor organización y separación de responsabilidades entre la interfaz
de usuario y la capa de servicios.

## Ejecución del proyecto

### Front-end React

1. Abrir una terminal en la carpeta `appguard-react`.

2. Instalar las dependencias:

```bash
npm install
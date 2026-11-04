import { render, screen } from "@testing-library/react";
import { describe, expect, test, vi } from "vitest";

import UsuarioItem from "./UsuarioItem";

describe("UsuarioItem", () => {
  test("muestra correctamente la información del usuario", () => {
    const usuario = {
      idUsuario: 1,
      idRol: 3,
      nombre: "Laura Camila",
      apellido: "Benavides Rojas",
      documento: "1067890123",
      correo: "laura.prueba@appguard.com",
      telefono: "3116789023",
      estado: "Activo",
    };

    render(
      <table>
        <tbody>
          <UsuarioItem
            usuario={usuario}
            onEditar={vi.fn()}
            onCambiarEstado={vi.fn()}
            onEliminar={vi.fn()}
          />
        </tbody>
      </table>
    );

    expect(screen.getByText(/Laura Camila/i)).toBeInTheDocument();
    expect(screen.getByText(/Benavides Rojas/i)).toBeInTheDocument();
    expect(screen.getByText("1067890123")).toBeInTheDocument();
    expect(
      screen.getByText("laura.prueba@appguard.com")
    ).toBeInTheDocument();
    expect(screen.getByText("3116789023")).toBeInTheDocument();
    expect(screen.getByText("Activo")).toBeInTheDocument();
  });
});
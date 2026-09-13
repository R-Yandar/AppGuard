USE appguard_db;
DESCRIBE usuarios;
DESCRIBE roles;
SELECT * FROM roles;
SELECT * FROM usuarios;
INSERT INTO roles (nombre_rol, descripcion)
VALUES
('Administrador', 'Gestiona usuarios y configuración del sistema'),
('Supervisor', 'Supervisa guardas y puestos asignados'),
('Guarda', 'Personal encargado de la vigilancia'),
('Cliente', 'Consulta información y servicios autorizados');
SELECT * FROM roles;
INSERT INTO usuarios
(id_rol, nombre, apellido, documento, correo, contrasena, telefono, estado)
VALUES
(1, 'Brayan', 'Rodriguez', '1000000001', 'brayan@appguard.com', 'AppGuard123', '3001234567', 'Activo');
SELECT * FROM usuarios;
UPDATE usuarios
SET telefono = '3109876543'
WHERE id_usuario = 1;
SELECT * FROM usuarios;
INSERT INTO usuarios
(id_rol, nombre, apellido, documento, correo, contrasena, telefono, estado)
VALUES
(3, 'Carlos', 'Perez', '1000000002', 'carlos@appguard.com', 'Prueba123', '3005556677', 'Activo');
SELECT * FROM usuarios;
DELETE FROM usuarios
WHERE id_usuario = 2;
SELECT * FROM usuarios;
USE appguard_db;
SELECT * FROM usuarios;
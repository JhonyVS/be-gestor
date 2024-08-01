CREATE TABLE usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    nacimiento DATE,
    email VARCHAR(85) NOT NULL UNIQUE,
    telefono VARCHAR(25),
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    motivo_suspension VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

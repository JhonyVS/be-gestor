CREATE TABLE tablero (
    id SERIAL PRIMARY KEY,
    id_proyecto INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);
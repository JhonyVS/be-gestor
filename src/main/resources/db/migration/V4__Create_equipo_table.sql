CREATE TABLE equipo (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_proyecto INT,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);
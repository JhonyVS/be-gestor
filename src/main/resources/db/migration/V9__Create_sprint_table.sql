CREATE TABLE sprint (
    id SERIAL PRIMARY KEY,
    id_proyecto INT,
    numero_sprint INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);

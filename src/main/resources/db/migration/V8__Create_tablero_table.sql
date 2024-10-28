CREATE TABLE tablero (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_proyecto UUID NULL,
    id_workspace UUID NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id),
    FOREIGN KEY (id_workspace) REFERENCES workspace(id)
);
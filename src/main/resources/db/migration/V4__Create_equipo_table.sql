CREATE TABLE equipo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    id_proyecto UUID,
    id_usuario_capitan UUID NOT NULL,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id),
    FOREIGN KEY (id_usuario_capitan) REFERENCES usuario(id)
);
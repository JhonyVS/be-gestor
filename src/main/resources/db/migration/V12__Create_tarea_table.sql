CREATE TABLE tarea (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_historia UUID,
    id_tarjeta UUID,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estimacion INT,
    id_usuario_asignado UUID,
    id_estado INT NOT NULL, -- Relación con la tabla estado_tarea
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_historia) REFERENCES historia(id),
    FOREIGN KEY (id_tarjeta) REFERENCES tarjeta(id),
    FOREIGN KEY (id_usuario_asignado) REFERENCES usuario(id),
    FOREIGN KEY (id_estado) REFERENCES estado_tarea(id) -- Clave foránea con estado_tarea
);

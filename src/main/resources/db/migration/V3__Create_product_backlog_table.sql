CREATE TABLE product_backlog (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_proyecto UUID,
    descripcion VARCHAR(200),
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);
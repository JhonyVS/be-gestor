CREATE TABLE historia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_product_backlog UUID,
    id_prioridad UUID,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estimacion INT,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_product_backlog) REFERENCES product_backlog(id),
    FOREIGN KEY (id_prioridad) REFERENCES prioridad(id)
);
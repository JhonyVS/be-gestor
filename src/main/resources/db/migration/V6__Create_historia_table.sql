CREATE TABLE historia (
    id SERIAL PRIMARY KEY,
    id_product_backlog INT,
    id_prioridad INT,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estimacion INT,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_product_backlog) REFERENCES product_backlog(id),
    FOREIGN KEY (id_prioridad) REFERENCES prioridad(id)
);
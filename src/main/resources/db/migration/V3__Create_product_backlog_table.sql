CREATE TABLE product_backlog (
    id SERIAL PRIMARY KEY,
    id_proyecto INT,
    descripcion VARCHAR(200),
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id)
);
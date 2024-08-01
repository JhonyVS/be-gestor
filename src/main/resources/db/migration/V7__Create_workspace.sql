CREATE TABLE workspace (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_project_manager UUID,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_project_manager) REFERENCES usuario(id)
);
CREATE TABLE sprint_backlog (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_sprint UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (id_sprint) REFERENCES sprint(id)
);
  
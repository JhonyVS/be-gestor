CREATE TABLE sprint_backlog (
    id SERIAL PRIMARY KEY,
    id_sprint INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (id_sprint) REFERENCES sprint(id)
);
  
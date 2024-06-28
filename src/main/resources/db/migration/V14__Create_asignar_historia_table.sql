CREATE TABLE asignar_historia (
    id_sprint_backlog INT,
    id_historia INT,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_sprint_backlog, id_historia),
    FOREIGN KEY (id_sprint_backlog) REFERENCES sprint_backlog(id),
    FOREIGN KEY (id_historia) REFERENCES historia(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
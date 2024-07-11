CREATE TABLE miembro (
    id_usuario UUID,
    id_equipo UUID,
    id_rol UUID,
    PRIMARY KEY (id_usuario, id_equipo, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_equipo) REFERENCES equipo(id),
    FOREIGN KEY (id_rol) REFERENCES rol(id),
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

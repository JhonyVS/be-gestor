CREATE TABLE tarjeta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_tablero UUID NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activado BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_tablero) REFERENCES tablero(id)
 );
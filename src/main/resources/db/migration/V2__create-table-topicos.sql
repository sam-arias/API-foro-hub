CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_de_creacion DATE,
    curso VARCHAR(255),
    status BOOLEAN,
    autor_id BIGINT NOT NULL,
    UNIQUE (titulo, mensaje(255)), -- Restringe duplicados en la combinación de título y mensaje

    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);


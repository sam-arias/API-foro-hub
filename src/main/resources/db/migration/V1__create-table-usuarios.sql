CREATE TABLE usuarios(
    id bigint NOT NULL auto_increment,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    clave VARCHAR(300) NOT NULL,
    enabled BOOLEAN,

    PRIMARY KEY(id)
);

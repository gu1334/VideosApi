CREATE TABLE categoria (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           titulo VARCHAR(180) NOT NULL,
                           cor VARCHAR(180) NOT NULL,
                           PRIMARY KEY (id)
);


CREATE TABLE banco (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       categoria_id BIGINT,
                       titulo VARCHAR(180) NOT NULL,
                       descricao VARCHAR(180) NOT NULL,
                       url VARCHAR(180) NOT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (categoria_id) REFERENCES categoria(id)
                           ON DELETE SET NULL
                           ON UPDATE CASCADE
);

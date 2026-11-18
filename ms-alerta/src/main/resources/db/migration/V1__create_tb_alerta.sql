CREATE TABLE tb_alerta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    idoso_id BIGINT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    gravidade VARCHAR(30) NOT NULL,
    origem VARCHAR(30) NOT NULL,
    mensagem VARCHAR(500) NOT NULL,
    data_criacao DATETIME NOT NULL,
    status VARCHAR(30) NOT NULL,
    status_sincronizado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
);

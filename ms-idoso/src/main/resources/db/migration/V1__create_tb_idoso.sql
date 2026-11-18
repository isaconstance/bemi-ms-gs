CREATE TABLE IF NOT EXISTS tb_idoso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    nome_contato_emergencia VARCHAR(100) NOT NULL,
    telefone_contato_emergencia VARCHAR(11) NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT pk_tb_idoso PRIMARY KEY (id),
    CONSTRAINT uk_tb_idoso_cpf UNIQUE (cpf)
    );
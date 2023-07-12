CREATE TABLE IF NOT EXISTS profissionais (
    id_profissional SERIAL NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cargo VARCHAR(13) NOT NULL,
    nascimento  DATE NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN NOT NULL,
    PRIMARY KEY (id_profissional)
);

CREATE TABLE IF NOT EXISTS contatos (
    id_contato SERIAL NOT NULL,
    nome VARCHAR(100) NOT NULL,
    contato VARCHAR(50) NOT NULL,
    id_profissional INT NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_contato),
    CONSTRAINT fk_id_profissional
        FOREIGN KEY (id_profissional)
        REFERENCES profissionais(id_profissional)
);
-- Cadastramento do profissionais
INSERT INTO profissionais(nome, cargo, nascimento, ativo)
VALUES ('Joao da Silva', 'DESENVOLVEDOR', '1980-02-02', true);

INSERT INTO profissionais(nome, cargo, nascimento, ativo)
VALUES ('Maria Eduarda', 'DESIGNER', '1992-05-03', true);

INSERT INTO profissionais(nome, cargo, nascimento, ativo)
VALUES ('Fernanda Martinez', 'SUPORTE', '1991-08-07', true);

INSERT INTO profissionais(nome, cargo, nascimento, ativo)
VALUES ('Eduardo Augusto', 'TESTER', '1970-06-08', false);


-- Cadastramento de Contatos
INSERT INTO contatos (nome, contato, id_profissional)
VALUES ('Telefone', '998354657', 1);

INSERT INTO contatos (nome, contato, id_profissional)
VALUES ('Email', 'joao.silva@email.com', 1);


CREATE DATABASE selecaoppgi;

CREATE TABLE IF NOT EXISTS Usuario
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	nome VARCHAR(80) NOT NULL,
	email VARCHAR(80) NOT NULL,
	senha VARCHAR(80) NOT NULL,
	papel INT NOT NULL DEFAULT 0,

	-- campos  de controle de login
	forcaResetSenha INT NOT NULL DEFAULT 0,
	dataUltimoLogin DATETIME,
	contadorLoginFalha INT NOT NULL DEFAULT 0,
	tokenLogin VARCHAR(256) NOT NULL DEFAULT "",
	dataTokenLogin DATETIME,

	-- login social
	socialID VARCHAR(500) NOT NULL DEFAULT "",
	socialOrigem INT NOT NULL DEFAULT 0,

	PRIMARY KEY (id)
);

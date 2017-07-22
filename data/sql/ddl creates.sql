CREATE DATABASE selecaoppgi;

-- como fazer o tratamento de vagas?

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

CREATE TABLE IF NOT EXISTS Edital
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	nome VARCHAR(80) NOT NULL,
	-- que configuracoes a mais ???
	status INT NOT NULL DEFAULT 0,
	
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EditalProvaEscrita
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	nome VARCHAR(20) NOT NULL,
	notaMinima FLOAT NOT NULL,
	peso FLOAT NOT NULL,
	obrigatoria INT NOT NULL,
	permiteDispensa INT NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EditalProjetoPesquisa
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	codigo CHAR(4) NOT NULL,
	nome VARCHAR(80) NOT NULL,
	descricao VARCHAR(2048) NOT NULL,
	exigeProvaOral INT NOT NULL,

	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EditalProjetoPesquisaProfessores
(
	id INT NOT NULL AUTO_INCREMENT,
	idProjetoPesquisa INT NOT NULL,
	idProfessor INT NOT NULL,

	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EditalProjetoPesquisaProvaEscrita
(
	id INT NOT NULL AUTO_INCREMENT,
	idProjetoPesquisa INT NOT NULL,
	idProvaEscrita INT NOT NULL,

	PRIMARY KEY (id)
);

CREATE TABLE EditalInscricao
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	idUsuario INT NOT NULL,
	idProjetoPesquisa1 INT NOT NULL,
	idProjetoPesquisa2 INT NOT NULL,
	idProjetoPesquisa3 INT NOT NULL,
	idProjetoPesquisa4 INT NOT NULL,
	idProjetoPesquisa5 INT NOT NULL,
	vagaDeficiente INT NOT NULL,
	vagaCotas INT NOT NULL,
	homologadoInicial INT NOT NULL,
	homologadoRecurso INT NOT NULL,
	aprovadoProvaEscrita INT NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE EditalInscricaoDispensa
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProvaEscrita INT NOT NULL,
	dispensaInicial INT NOT NULL,
	dispensaRecurso INT NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE EditalInscricaoAvaliacaoProvaEscrita
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProvaEscrita INT NOT NULL,
	presente INT NOT NULL,
	notaInicial FLOAT NOT NULL,
	notaRecurso FLOAT NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE EditalInscricaoAvaliacaoProvaOral
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProjetoPesquisa INT NOT NULL,
	presente INT NOT NULL,
	notaInicial FLOAT NOT NULL,
	notaRecurso FLOAT NOT NULL,
	
	PRIMARY KEY (id)
);

CREATE TABLE EditalInscricaoAvaliacaoAlinhamento
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProjetoPesquisa INT NOT NULL,
	notaInicial FLOAT NOT NULL,
	notaRecurso FLOAT NOT NULL,
	-- aqui sao tres componentes da nota, certo?
	
	PRIMARY KEY (id)
);

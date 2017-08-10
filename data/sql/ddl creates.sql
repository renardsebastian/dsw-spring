CREATE DATABASE selecaoppgi;

CREATE TABLE IF NOT EXISTS Usuario
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	nome VARCHAR(80) NOT NULL,
	cpf VARCHAR(14) NOT NULL DEFAULT "",
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
	
	-- edital selecionado
	idEditalSelecionado INT,

	PRIMARY KEY(id),
    FOREIGN KEY(idEditalSelecionado) REFERENCES Edital(id)  
);

CREATE TABLE IF NOT EXISTS Edital
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	nome VARCHAR(80) NOT NULL,
	dataInicio DATETIME,
	dataTermino DATETIME,
	status INT NOT NULL DEFAULT 0,
	notaMinimaAlinhamento INT,
	
	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS EditalComissaoSelecao
(
	id INT NOT NULL AUTO_INCREMENT,
	idEdital INT NOT NULL,
	idProfessor INT NOT NULL,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idEdital) REFERENCES Edital(id),
    FOREIGN KEY(idProfessor) REFERENCES Usuario(id)  
);

CREATE TABLE IF NOT EXISTS EditalComissaoRecursos
(
	id INT NOT NULL AUTO_INCREMENT,
	idEdital INT NOT NULL,
	idProfessor INT NOT NULL,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idEdital) REFERENCES Edital(id),
    FOREIGN KEY(idProfessor) REFERENCES Usuario(id)  
);

CREATE TABLE IF NOT EXISTS EditalProvaEscrita
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	sigla CHAR(4) NOT NULL,
	nome VARCHAR(80) NOT NULL,
	dispensavel INT NOT NULL,
	notaMinimaAprovacao INT NOT NULL,

	PRIMARY KEY(id),
    FOREIGN KEY(idEdital) REFERENCES Edital(id)
);

CREATE TABLE IF NOT EXISTS EditalProvaEscritaQuestao
(
	id INT NOT NULL AUTO_INCREMENT,
	idProvaEscrita INT NOT NULL,
	numeroOrdem INT NOT NULL,
	peso INT NOT NULL,

	PRIMARY KEY(id),
    FOREIGN KEY(idProvaEscrita) REFERENCES EditalProvaEscrita(id)
);

CREATE TABLE IF NOT EXISTS EditalProjetoPesquisa
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	codigo CHAR(4) NOT NULL,
	nome VARCHAR(80) NOT NULL,
	exigeProvaOral INT NOT NULL,

	PRIMARY KEY(id),
    FOREIGN KEY(idEdital) REFERENCES Edital(id)
);

CREATE TABLE IF NOT EXISTS EditalProjetoPesquisaProfessor
(
	id INT NOT NULL AUTO_INCREMENT,
	idProjetoPesquisa INT NOT NULL,
	idProfessor INT NOT NULL,

	PRIMARY KEY(id),
    FOREIGN KEY(idProjetoPesquisa) REFERENCES EditalProjetoPesquisa(id),
    FOREIGN KEY(idProfessor) REFERENCES Usuario(id)
);

CREATE TABLE IF NOT EXISTS EditalProjetoPesquisaProvaEscrita
(
	id INT NOT NULL AUTO_INCREMENT,
	idProjetoPesquisa INT NOT NULL,
	idProvaEscrita INT NOT NULL,

	PRIMARY KEY(id),
    FOREIGN KEY(idProjetoPesquisa) REFERENCES EditalProjetoPesquisa(id),
    FOREIGN KEY(idProvaEscrita) REFERENCES EditalProvaEscrita(id)
);

CREATE TABLE IF NOT EXISTS EditalCriterioAlinhamento
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	numeroOrdem INT NOT NULL,
	nome VARCHAR(80) NOT NULL,
	pesoComProvaOral INT NOT NULL,
	pesoSemProvaOral INT NOT NULL,
	pertenceProvaOral INT NOT NULL,

	PRIMARY KEY(id),
    FOREIGN KEY(idEdital) REFERENCES Edital(id)
);

CREATE TABLE IF NOT EXISTS EditalSubcriterioAlinhamento
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idCriterio INT NOT NULL,
	numeroOrdem INT NOT NULL,
	nome VARCHAR(80) NOT NULL,
	descricao VARCHAR(8192) NOT NULL,
	peso INT NOT NULL,

	PRIMARY KEY(id),
    FOREIGN KEY(idCriterio) REFERENCES EditalCriterioAlinhamento(id)
);

CREATE TABLE IF NOT EXISTS Inscricao
(
	id INT NOT NULL AUTO_INCREMENT,
	dataRegistro DATETIME NOT NULL,
	dataAtualizacao DATETIME NOT NULL,
	idEdital INT NOT NULL,
	idCandidato INT NOT NULL,
	cotaNegros INT NOT NULL,
	cotaDeficientes INT NOT NULL,
	homologadoInicial INT NOT NULL,
	justificativaHomologacaoInicial VARCHAR(4096),
	homologadoRecurso INT NOT NULL,
	justificativaHomologacaoRecurso VARCHAR(4096),
	dispensadoProvaInicial INT NOT NULL,
	justificativaDispensaInicial VARCHAR(4096),
	dispensadoProvaRecurso INT NOT NULL,
	justificativaDispensaRecurso VARCHAR(4096),
	
	PRIMARY KEY(id),
    FOREIGN KEY(idEdital) REFERENCES Edital(id),
    FOREIGN KEY(idCandidato) REFERENCES Usuario(id)
);

CREATE TABLE IF NOT EXISTS InscricaoProjetoPesquisa
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProjetoPesquisa INT NOT NULL,
	numeroOrdem INT NOT NULL,
	intencoes VARCHAR(8192) NOT NULL,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricao) REFERENCES Inscricao(id),
    FOREIGN KEY(idProjetoPesquisa) REFERENCES EditalProjetoPesquisa(id)
);

CREATE TABLE IF NOT EXISTS InscricaoProvaEscrita
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProvaEscrita INT NOT NULL,
	presente INT NOT NULL,
	notaFinal INT,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricao) REFERENCES Inscricao(id),
    FOREIGN KEY(idProvaEscrita) REFERENCES EditalProvaEscrita(id)
);

CREATE TABLE IF NOT EXISTS InscricaoProvaEscritaQuestao
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricaoProva INT NOT NULL,
	idQuestao INT NOT NULL,
	notaInicial INT NOT NULL,
	notaRecurso INT NOT NULL,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricaoProva) REFERENCES InscricaoProvaEscrita(id),
    FOREIGN KEY(idQuestao) REFERENCES InscricaoProvaEscritaQuestao(id)
);

CREATE TABLE IF NOT EXISTS InscricaoProvaAlinhamento
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProjetoPesquisa INT NOT NULL,
	presenteProvaOral INT,
	justificativaNotasInicial VARCHAR(4096),
	justificativaNotasRecurso VARCHAR(4096),
	notaFinal INT,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricao) REFERENCES Inscricao(id),
    FOREIGN KEY(idProjetoPesquisa) REFERENCES EditalProjetoPesquisa(id)
);

CREATE TABLE IF NOT EXISTS InscricaoProvaAlinhamentoSubcriterio
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricaoAlinhamento INT NOT NULL,
	idSubcriterio INT NOT NULL,
	notaInicial INT,
	notaRecurso INT,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricaoAlinhamento) REFERENCES InscricaoProvaAlinhamento(id),
    FOREIGN KEY(idSubcriterio) REFERENCES EditalSubcriterioAlinhamento(id)
);

CREATE TABLE IF NOT EXISTS InscricaoSelecao
(
	id INT NOT NULL AUTO_INCREMENT,
	idInscricao INT NOT NULL,
	idProjetoPesquisa INT NOT NULL,
	numeroOrdem INT,
	classificado INT,
	idOrientador INT,
	idCoorientador INT,
	
	PRIMARY KEY(id),
    FOREIGN KEY(idInscricao) REFERENCES Inscricao(id),
    FOREIGN KEY(idProjetoPesquisa) REFERENCES EditalProjetoPesquisa(id),
    FOREIGN KEY(idOrientador) REFERENCES Usuario(id),
    FOREIGN KEY(idCoorientador) REFERENCES Usuario(id)
);

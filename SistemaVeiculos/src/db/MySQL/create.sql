CREATE DATABASE SistemaVeiculos;

USE SistemaVeiculos;

CREATE TABLE Cliente
(
    cpf            VARCHAR(11)  NOT NULL UNIQUE,
    nome           VARCHAR(256) NOT NULL,
    email          VARCHAR(256) NOT NULL UNIQUE,
    senha          VARCHAR(256) NOT NULL,
    sexo           VARCHAR(1)   NOT NULL,
    telefone       VARCHAR(15)  NOT NULL,
    dataNascimento DATE         NOT NULL,
    papel          VARCHAR(7)   NOT NULL,

    PRIMARY KEY (cpf)
);

CREATE TABLE Loja
(
    cnpj      VARCHAR(14)  NOT NULL UNIQUE,
    nome      VARCHAR(256) NOT NULL,
    email     VARCHAR(256) NOT NULL UNIQUE,
    senha     VARCHAR(256) NOT NULL,
    descricao VARCHAR(256) NOT NULL,

    PRIMARY KEY (cnpj)
);

CREATE TABLE Veiculo
(
    cnpj      VARCHAR(14)  NOT NULL,
    placa     VARCHAR(7)   NOT NULL UNIQUE,
    modelo    VARCHAR(256) NOT NULL,
    chassi    VARCHAR(17)  NOT NULL UNIQUE,
    ano       INT(4)       NOT NULL,
    km        FLOAT        NOT NULL,
    descricao VARCHAR(256) NOT NULL,
    valor     FLOAT        NOT NULL,

    FOREIGN KEY (cnpj) REFERENCES Loja (cnpj) ON DELETE CASCADE,
    PRIMARY KEY (chassi)
);

CREATE TABLE Foto
(
    chassi   VARCHAR(17)  NOT NULL,
    pathFoto VARCHAR(256) NOT NULL UNIQUE,

    FOREIGN KEY (chassi) REFERENCES Veiculo (chassi) ON DELETE CASCADE,
    PRIMARY KEY (pathFoto)
);

CREATE TABLE Proposta
(
    cpf       VARCHAR(11)  NOT NULL,
    cnpj      VARCHAR(14)  NOT NULL,
    chassi    VARCHAR(17)  NOT NULL,
    valor     FLOAT        NOT NULL,
    pagamento VARCHAR(256) NOT NULL,
    data      DATE     NOT NULL,
    status    INT(1)       NOT NULL,
    CONSTRAINT CK_status CHECK (status = 0 OR status = 1 OR status = 2 OR status = 3),

    FOREIGN KEY (cpf) REFERENCES Cliente (cpf) ON DELETE CASCADE,
    FOREIGN KEY (cnpj) REFERENCES Loja (cnpj) ON DELETE CASCADE,
    FOREIGN KEY (chassi) REFERENCES Veiculo (chassi) ON DELETE CASCADE,
    PRIMARY KEY (cpf, chassi)
);

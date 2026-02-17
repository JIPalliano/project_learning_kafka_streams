DROP TABLE IF EXISTS foto_ocorrencia;
DROP TABLE IF EXISTS ocorrencia;
DROP TABLE IF EXISTS endereco;
DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
                         cod_cliente BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         nme_cliente VARCHAR(255) NOT NULL,
                         dta_nascimento DATE,
                         nro_cpf VARCHAR(14) NOT NULL UNIQUE,
                         dta_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE endereco (
                          cod_endereco BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          nme_logradouro VARCHAR(255) NOT NULL,
                          nme_bairro VARCHAR(100),
                          nro_cep VARCHAR(10) NOT NULL,
                          nme_cidade VARCHAR(100) NOT NULL,
                          nme_estado VARCHAR(2) NOT NULL
);

CREATE TABLE ocorrencia (
                            cod_ocorrencia BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            cod_cliente BIGINT NOT NULL,
                            cod_endereco BIGINT NOT NULL,
                            dta_ocorrencia TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            sta_ocorrencia VARCHAR(20) NOT NULL CHECK (sta_ocorrencia IN ('ATIVA', 'FINALIZADA')),

                            CONSTRAINT fk_ocorrencia_cliente FOREIGN KEY (cod_cliente) REFERENCES cliente(cod_cliente),
                            CONSTRAINT fk_ocorrencia_endereco FOREIGN KEY (cod_endereco) REFERENCES endereco(cod_endereco)
);

CREATE TABLE foto_ocorrencia (
                                 cod_foto_ocorrencia BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 cod_ocorrencia BIGINT NOT NULL,
                                 dta_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 dsc_path_bucket VARCHAR(500) NOT NULL,
                                 dsc_hash VARCHAR(255),

                                 CONSTRAINT fk_foto_ocorrencia FOREIGN KEY (cod_ocorrencia) REFERENCES ocorrencia(cod_ocorrencia)
);

CREATE INDEX idx_ocorrencia_cliente ON ocorrencia(cod_cliente);
CREATE INDEX idx_ocorrencia_endereco ON ocorrencia(cod_endereco);
CREATE INDEX idx_foto_ocorrencia_pk ON foto_ocorrencia(cod_ocorrencia);
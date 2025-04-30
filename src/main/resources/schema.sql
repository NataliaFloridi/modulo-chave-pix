-- Tabela para armazenar as chaves PIX
CREATE TABLE IF NOT EXISTS chaves_pix (
    id UUID PRIMARY KEY,
    tipo_chave VARCHAR(9) NOT NULL,
    valor_chave VARCHAR(77) NOT NULL,
    tipo_conta VARCHAR(10) NOT NULL,
    numero_agencia NUMERIC(4) NOT NULL,
    numero_conta NUMERIC(8) NOT NULL,
    nome_correntista VARCHAR(30) NOT NULL,
    sobrenome_correntista VARCHAR(45),
    data_inclusao TIMESTAMP NOT NULL,
    data_inativacao TIMESTAMP,
    tipo_pessoa VARCHAR(11),
    CONSTRAINT chk_tipo_chave CHECK (tipo_chave IN ('CELULAR', 'EMAIL', 'CPF', 'CNPJ', 'ALEATORIO')),
    CONSTRAINT chk_tipo_conta CHECK (tipo_conta IN ('CORRENTE', 'POUPANCA')),
    CONSTRAINT chk_tipo_pessoa CHECK (tipo_pessoa IN ('FISICA', 'JURIDICA')),
    CONSTRAINT uk_valor_chave UNIQUE (valor_chave)
);

-- Índices para otimizar as consultas
CREATE INDEX IF NOT EXISTS idx_chave_pix_tipo ON chaves_pix(tipo_chave);
CREATE INDEX IF NOT EXISTS idx_chave_pix_agencia_conta ON chaves_pix(numero_agencia, numero_conta);
CREATE INDEX IF NOT EXISTS idx_chave_pix_nome ON chaves_pix(nome_correntista);
CREATE INDEX IF NOT EXISTS idx_chave_pix_data_inclusao ON chaves_pix(data_inclusao); 

--inserir dados
INSERT INTO chaves_pix (id, tipo_chave, valor_chave, tipo_conta, numero_agencia, numero_conta, nome_correntista, sobrenome_correntista, data_inclusao, data_inativacao, tipo_pessoa) VALUES
('123e4567-e89b-12d3-a456-426614174000', 'CELULAR', '5511999999999', 'CORRENTE', '1234', '56789012', 'João', 'da Silva', '2021-01-01', '2021-01-01', 'FISICA');
INSERT INTO chaves_pix (id, tipo_chave, valor_chave, tipo_conta, numero_agencia, numero_conta, nome_correntista, sobrenome_correntista, data_inclusao, data_inativacao, tipo_pessoa) VALUES
('123e4567-e89b-12d3-a456-426614174001', 'EMAIL', 'joao.silva@gmail.com', 'CORRENTE', '1234', '56789012', 'João', 'da Silva', '2021-01-01', null, 'FISICA');
INSERT INTO chaves_pix (id, tipo_chave, valor_chave, tipo_conta, numero_agencia, numero_conta, nome_correntista, sobrenome_correntista, data_inclusao, data_inativacao, tipo_pessoa) VALUES
('123e4567-e89b-12d3-a456-426614174002', 'CPF', '12345678901', 'CORRENTE', '1234', '56789012', 'João', 'da Silva', '2021-01-01', null, 'FISICA');
INSERT INTO chaves_pix (id, tipo_chave, valor_chave, tipo_conta, numero_agencia, numero_conta, nome_correntista, sobrenome_correntista, data_inclusao, data_inativacao, tipo_pessoa) VALUES
('123e4567-e89b-12d3-a456-426614174003', 'CNPJ', '12345678901234', 'CORRENTE', '4567', '89012345', 'João', 'da Silva', '2021-01-01', null, 'JURIDICA');
INSERT INTO chaves_pix (id, tipo_chave, valor_chave, tipo_conta, numero_agencia, numero_conta, nome_correntista, sobrenome_correntista, data_inclusao, data_inativacao, tipo_pessoa) VALUES
('123e4567-e89b-12d3-a456-426614174004', 'ALEATORIO', '98765432109876', 'CORRENTE', '4567', '89012345', 'João', 'da Silva', '2021-01-01', null , 'JURIDICA');
INSERT INTO chaves_pix (id, tipo_chave, valor_chave, tipo_conta, numero_agencia, numero_conta, nome_correntista, sobrenome_correntista, data_inclusao, data_inativacao, tipo_pessoa) VALUES
('123e4567-e89b-12d3-a456-426614174005', 'ALEATORIO', '98765432109877', 'CORRENTE', '4567', '89012345', 'João', 'da Silva', '2021-01-01', '2021-01-01', 'JURIDICA');
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
    data_criacao TIMESTAMP NOT NULL,
    CONSTRAINT chk_tipo_chave CHECK (tipo_chave IN ('CELULAR', 'EMAIL', 'CPF', 'CNPJ', 'ALEATORIO')),
    CONSTRAINT chk_tipo_conta CHECK (tipo_conta IN ('CORRENTE', 'POUPANCA')),
    CONSTRAINT uk_valor_chave UNIQUE (valor_chave)
);

-- Índices para otimizar as consultas
CREATE INDEX IF NOT EXISTS idx_chave_pix_tipo ON chaves_pix(tipo_chave);
CREATE INDEX IF NOT EXISTS idx_chave_pix_agencia_conta ON chaves_pix(numero_agencia, numero_conta);
CREATE INDEX IF NOT EXISTS idx_chave_pix_nome ON chaves_pix(nome_correntista);
CREATE INDEX IF NOT EXISTS idx_chave_pix_data_criacao ON chaves_pix(data_criacao); 
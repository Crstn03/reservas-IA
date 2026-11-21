-- Tabla de cuentas bancarias
CREATE TABLE accounts (
    id          BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20)  NOT NULL UNIQUE,
    owner_name  VARCHAR(120) NOT NULL,
    balance     NUMERIC(18, 2) NOT NULL DEFAULT 0.00,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_balance_non_negative CHECK (balance >= 0)
);

-- Tabla de transacciones (histórico de traslados)
CREATE TABLE transactions (
    id              BIGSERIAL PRIMARY KEY,
    source_account_id   BIGINT NOT NULL REFERENCES accounts(id),
    target_account_id   BIGINT NOT NULL REFERENCES accounts(id),
    amount          NUMERIC(18, 2) NOT NULL,
    description     VARCHAR(255),
    status          VARCHAR(20) NOT NULL DEFAULT 'COMPLETADA',
    executed_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    qr_code         TEXT,
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_status CHECK (status IN ('COMPLETADA', 'FALLIDA')),
    CONSTRAINT chk_different_accounts CHECK (source_account_id != target_account_id)
);

CREATE INDEX idx_transaction_source ON transactions(source_account_id);
CREATE INDEX idx_transaction_target ON transactions(target_account_id);
CREATE INDEX idx_transaction_date   ON transactions(executed_at DESC);

-- Cuentas de ejemplo para pruebas
INSERT INTO accounts (account_number, owner_name, balance) VALUES
    ('001-100-001', 'Diego Hernandez',   5000000.00),
    ('001-100-002', 'Maria Lopez',       2500000.00),
    ('001-100-003', 'Carlos Restrepo',   8000000.00),
    ('001-100-004', 'Ana Martinez',      1200000.00);

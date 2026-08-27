-- ===================================================================
-- V1: esquema inicial - portfolio-tracker
-- Orden de creacion respeta dependencias de FK (padres antes que hijos)
-- ===================================================================

CREATE TABLE app_user (
                          id_user     BIGSERIAL PRIMARY KEY,
                          name        VARCHAR(100) NOT NULL,
                          date_birth  DATE,
                          created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE user_auth (
                           id_user       BIGINT PRIMARY KEY REFERENCES app_user(id_user) ON DELETE CASCADE,
                           username      VARCHAR(50) NOT NULL,
                           email         VARCHAR(70) NOT NULL,
                           password_hash VARCHAR(70) NOT NULL,
                           created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
                           updated_at    TIMESTAMP NOT NULL DEFAULT NOW(),
                           CONSTRAINT uq_user_auth_username UNIQUE (username),
                           CONSTRAINT uq_user_auth_email UNIQUE (email)
);

CREATE TABLE portfolio (
                           id_portfolio BIGSERIAL PRIMARY KEY,
                           id_user      BIGINT NOT NULL REFERENCES app_user(id_user) ON DELETE CASCADE,
                           balance      DECIMAL(19,4) NOT NULL DEFAULT 0,
                           created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
                           updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    -- fuerza la cardinalidad 1-a-1 acordada: un usuario, un portfolio
                           CONSTRAINT uq_portfolio_id_user UNIQUE (id_user)
);

CREATE TABLE asset (
                       id_asset        BIGSERIAL PRIMARY KEY,
                       name            VARCHAR(100) NOT NULL,
                       ticker          VARCHAR(10) NOT NULL,
                       asset_type      VARCHAR(20) NOT NULL,
                       external_source VARCHAR(50),
                       created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
                       updated_at      TIMESTAMP NOT NULL DEFAULT NOW(),
                       CONSTRAINT uq_asset_ticker UNIQUE (ticker),
                       CONSTRAINT chk_asset_type CHECK (asset_type IN ('STOCK', 'CRYPTO'))
);

CREATE TABLE transaction (
                             id_transaction   BIGSERIAL PRIMARY KEY,
                             id_portfolio     BIGINT NOT NULL REFERENCES portfolio(id_portfolio) ON DELETE CASCADE,
                             id_asset         BIGINT NOT NULL REFERENCES asset(id_asset),
                             quantity         DECIMAL(19,8) NOT NULL,
                             price            DECIMAL(19,8) NOT NULL,
                             transaction_type CHAR(1) NOT NULL,
                             transaction_date TIMESTAMP NOT NULL DEFAULT NOW(),
                             created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
                             CONSTRAINT chk_transaction_type CHECK (transaction_type IN ('B', 'S')), -- Buy / Sell
                             CONSTRAINT chk_quantity_positive CHECK (quantity > 0),
                             CONSTRAINT chk_price_positive CHECK (price > 0)
);

CREATE TABLE audit (
                       id_audit    BIGSERIAL PRIMARY KEY,
                       table_name  VARCHAR(50) NOT NULL,
                       action      VARCHAR(10) NOT NULL,
                       id_user     BIGINT REFERENCES app_user(id_user),
                       date_action TIMESTAMP NOT NULL DEFAULT NOW(),
                       CONSTRAINT chk_audit_action CHECK (action IN ('INSERT', 'UPDATE', 'DELETE'))
    );

-- Indices para las consultas mas frecuentes (historial y reportes)
CREATE INDEX idx_transaction_portfolio ON transaction(id_portfolio);
CREATE INDEX idx_transaction_asset ON transaction(id_asset);
CREATE INDEX idx_transaction_date ON transaction(transaction_date);
CREATE INDEX idx_audit_user ON audit(id_user);
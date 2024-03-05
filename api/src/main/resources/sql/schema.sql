CREATE TABLE IF NOT EXISTS wallet
(
    valletId VARCHAR(250) UNIQUE,
    amount BIGINT NOT NULL,
    CONSTRAINT pk_wallet PRIMARY KEY (valletId)
);
CREATE TABLE IF NOT EXISTS providers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);


CREATE TABLE IF NOT EXISTS visitors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    status VARCHAR(12) NOT NULL,
    provider BIGINT,
    arrival_time timestamp NOT NULL DEFAULT NOW(),
    call_time timestamp,
    completed_time timestamp,
    FOREIGN KEY (provider) REFERENCES providers(id)
);
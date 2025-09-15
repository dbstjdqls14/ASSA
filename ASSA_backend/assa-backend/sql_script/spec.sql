CREATE TABLE spec (
    spec_id SERIAL PRIMARY KEY,
    cpu VARCHAR(50),
    ram VARCHAR(50),
    camera VARCHAR(50),
    battery VARCHAR(50),
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
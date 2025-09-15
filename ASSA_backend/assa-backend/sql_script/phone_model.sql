CREATE TABLE phone (
    phone_id SERIAL PRIMARY KEY,
    spec_id INTEGER REFERENCES spec(spec_id),
    name VARCHAR(50) NOT NULL,
    brand_id INTEGER NOT NULL REFERENCES brand(brand_id),
    capacity_id INTEGER REFERENCES capacity(capacity_id),
    color VARCHAR(100),
    phone_image VARCHAR(50),
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

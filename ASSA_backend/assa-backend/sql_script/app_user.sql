CREATE TABLE app_user(
    user_id SERIAL PRIMARY KEY,
    region_metro_id INTEGER,
    region_district_id INTEGER,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(60) NOT NULL,
    phone_id INTEGER,
    profile_path VARCHAR(300),
    is_deleted BOOLEAN DEFAULT FALSE,
    deleted_time TIMESTAMP,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE app_user 
ADD CONSTRAINT fk_user_region_metro 
FOREIGN KEY (region_metro_id) REFERENCES region_metro(region_metro_id);

ALTER TABLE app_user 
ADD CONSTRAINT fk_user_region_district 
FOREIGN KEY (region_district_id) REFERENCES region_district(region_district_id);

ALTER TABLE app_user 
ADD CONSTRAINT fk_user_phone 
FOREIGN KEY (phone_id) REFERENCES phone(phone_id);

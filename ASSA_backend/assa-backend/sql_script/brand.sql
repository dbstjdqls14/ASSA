CREATE TABLE brand (
    brand_id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 기본 데이터 삽입
INSERT INTO brand (brand_id, name) VALUES
(101, '삼성'),
(102, '애플'),
(103, 'LG'),
(104, '샤오미'),
(105, '노키아'),
(106, '기타');

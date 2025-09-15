CREATE TABLE capacity (
    capacity_id SERIAL PRIMARY KEY,
    size VARCHAR(50) NOT NULL UNIQUE,
    created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 기본 데이터 삽입
INSERT INTO capacity (capacity_id, size) VALUES
(201, '32'),
(202, '64'),
(203, '128'),
(204, '256'),
(205, '512'),
(206, '1024'),
(207, '2048'),
(200, '기타');

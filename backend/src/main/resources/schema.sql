CREATE TABLE charging_points (
  id BIGINT PRIMARY KEY,
  ampers INTEGER,
  status VARCHAR,
  car_id VARCHAR,
  parking_time TIMESTAMP,
  UNIQUE KEY car_id_UNIQUE (car_id)
);